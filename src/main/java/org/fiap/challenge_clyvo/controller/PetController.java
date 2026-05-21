package org.fiap.challenge_clyvo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.fiap.challenge_clyvo.dto.PetRequestDTO;
import org.fiap.challenge_clyvo.dto.PetResponseDTO;
import org.fiap.challenge_clyvo.service.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pets", description = "Gerenciamento de pets")
@RestController
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "Listar pets com filtros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<PetResponseDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String raca,
            @RequestParam(required = false) Long responsavelId,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        if (nome != null && !nome.isBlank() && raca != null && !raca.isBlank()) {
            return ResponseEntity.ok(petService.buscarPorNomeERaca(nome, raca, pageable));
        }
        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(petService.buscarPorNome(nome, pageable));
        }
        if (raca != null && !raca.isBlank()) {
            return ResponseEntity.ok(petService.buscarPorRaca(raca, pageable));
        }
        if (responsavelId != null) {
            return ResponseEntity.ok(petService.buscarPorResponsavel(responsavelId, pageable));
        }

        return ResponseEntity.ok(petService.listarTodos(pageable));
    }

    @Operation(summary = "Buscar pet por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pet encontrado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar novo pet")
    @ApiResponse(responseCode = "201", description = "Criado com sucesso")
    @PostMapping
    public ResponseEntity<PetResponseDTO> salvar(@Valid @RequestBody PetRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.salvar(dto));
    }

    @Operation(summary = "Atualizar pet")
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody PetRequestDTO dto) {
        return ResponseEntity.ok(petService.atualizar(id, dto));
    }

    @Operation(summary = "Deletar pet")
    @ApiResponse(responseCode = "204", description = "Deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}