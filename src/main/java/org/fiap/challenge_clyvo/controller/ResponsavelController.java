package org.fiap.challenge_clyvo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.fiap.challenge_clyvo.dto.ResponsavelDTO;
import org.fiap.challenge_clyvo.service.ResponsavelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Responsáveis", description = "Gerenciamento de responsáveis")
@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {
    private final ResponsavelService responsavelService;

    public ResponsavelController(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    @Operation(summary = "Listar responsáveis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<ResponsavelDTO>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        if (nome != null && !nome.isBlank()) {
            return ResponseEntity.ok(responsavelService.buscarPorNome(nome, pageable));
        }

        return ResponseEntity.ok(responsavelService.listarTodos(pageable));
    }

    @Operation(summary = "Buscar responsável por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsável encontrado"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(responsavelService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar responsável")
    @ApiResponse(responseCode = "201", description = "Criado com sucesso")
    @PostMapping
    public ResponseEntity<ResponsavelDTO> salvar(@Valid @RequestBody ResponsavelDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.salvar(dto));
    }

    @Operation(summary = "Atualizar responsável")
    @PutMapping("/{id}")
    public ResponseEntity<ResponsavelDTO> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody ResponsavelDTO dto) {
        return ResponseEntity.ok(responsavelService.atualizar(id, dto));
    }

    @Operation(summary = "Deletar responsável")
    @ApiResponse(responseCode = "204", description = "Deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        responsavelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}