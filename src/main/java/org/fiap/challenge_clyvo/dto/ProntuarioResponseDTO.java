package org.fiap.challenge_clyvo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "DTO de saída com dados do prontuário")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProntuarioResponseDTO {
    @Schema(description = "ID do prontuário", example = "1")
    private Long id;

    @Schema(description = "Procedimento realizado", example = "Vacinação antirrábica")
    private String procedimento;

    @Schema(description = "Data do atendimento", example = "2026-05-20")
    private LocalDate dataProcedimento;

    @Schema(description = "Local do atendimento", example = "Clínica Clyvo - Unidade Paulista")
    private String localAtendimento;

    @Schema(description = "ID do pet", example = "1")
    private Long petId;

    @Schema(description = "Nome do pet", example = "Thor")
    private String nomePet;

    @Schema(description = "ID do veterinário", example = "2")
    private Long veterinarioId;

    @Schema(description = "Nome do veterinário", example = "Dr. Carlos Souza")
    private String nomeVeterinario;
}