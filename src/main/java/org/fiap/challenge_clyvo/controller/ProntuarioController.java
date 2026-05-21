package org.fiap.challenge_clyvo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.fiap.challenge_clyvo.dto.ProntuarioRequestDTO;
import org.fiap.challenge_clyvo.dto.ProntuarioResponseDTO;
import org.fiap.challenge_clyvo.service.ProntuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Prontuários", description = "Gerenciamento de prontuários dos pets")
@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {
    private final ProntuarioService prontuarioService;

    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @Operation(summary = "Listar prontuários com filtros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<ProntuarioResponseDTO>> listar(
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) Long veterinarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @PageableDefault(size = 10, sort = "dataProcedimento") Pageable pageable) {

        if (petId != null && veterinarioId != null) {
            return ResponseEntity.ok(prontuarioService.buscarPorPetEVeterinario(petId, veterinarioId, pageable));
        }
        if (petId != null) {
            return ResponseEntity.ok(prontuarioService.buscarPorPet(petId, pageable));
        }
        if (veterinarioId != null) {
            return ResponseEntity.ok(prontuarioService.buscarPorVeterinario(veterinarioId, pageable));
        }
        if (dataInicio != null && dataFim != null) {
            return ResponseEntity.ok(prontuarioService.buscarPorPeriodo(dataInicio, dataFim, pageable));
        }
        if (data != null) {
            return ResponseEntity.ok(prontuarioService.buscarPorData(data, pageable));
        }

        return ResponseEntity.ok(prontuarioService.listarTodos(pageable));
    }

    @Operation(summary = "Buscar prontuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prontuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProntuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prontuarioService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar novo prontuário")
    @ApiResponse(responseCode = "201", description = "Criado com sucesso")
    @PostMapping
    public ResponseEntity<ProntuarioResponseDTO> salvar(@Valid @RequestBody ProntuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prontuarioService.salvar(dto));
    }

    @Operation(summary = "Atualizar prontuário")
    @PutMapping("/{id}")
    public ResponseEntity<ProntuarioResponseDTO> atualizar(@PathVariable Long id,
                                                           @Valid @RequestBody ProntuarioRequestDTO dto) {
        return ResponseEntity.ok(prontuarioService.atualizar(id, dto));
    }

    @Operation(summary = "Deletar prontuário")
    @ApiResponse(responseCode = "204", description = "Deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        prontuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}