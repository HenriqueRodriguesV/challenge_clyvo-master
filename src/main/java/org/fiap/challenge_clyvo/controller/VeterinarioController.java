package org.fiap.challenge_clyvo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.fiap.challenge_clyvo.dto.VeterinarioDTO;
import org.fiap.challenge_clyvo.service.VeterinarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Veterinários", description = "Gerenciamento de veterinários")
@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {
    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @Operation(summary = "Listar veterinários com filtros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<VeterinarioDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String crmv,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(veterinarioService.buscarPorNome(nome, pageable));
        }
        if (crmv != null && !crmv.isBlank()) {
            return ResponseEntity.ok(veterinarioService.buscarPorCrmv(crmv, pageable));
        }

        return ResponseEntity.ok(veterinarioService.listarTodos(pageable));
    }

    @Operation(summary = "Buscar veterinário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veterinário encontrado"),
            @ApiResponse(responseCode = "404", description = "Veterinário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar veterinário")
    @ApiResponse(responseCode = "201", description = "Criado com sucesso")
    @PostMapping
    public ResponseEntity<VeterinarioDTO> salvar(@Valid @RequestBody VeterinarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.salvar(dto));
    }

    @Operation(summary = "Atualizar veterinário")
    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioDTO> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody VeterinarioDTO dto) {
        return ResponseEntity.ok(veterinarioService.atualizar(id, dto));
    }

    @Operation(summary = "Deletar veterinário")
    @ApiResponse(responseCode = "204", description = "Deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}