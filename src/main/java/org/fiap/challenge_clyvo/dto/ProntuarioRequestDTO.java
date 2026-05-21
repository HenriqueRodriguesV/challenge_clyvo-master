package org.fiap.challenge_clyvo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "DTO de entrada para cadastro e atualização de prontuário")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProntuarioRequestDTO {
    @Schema(description = "Descrição do procedimento realizado", example = "Vacinação antirrábica")
    @NotBlank(message = "Procedimento é obrigatório")
    @Size(max = 255, message = "Procedimento deve ter no máximo 255 caracteres")
    private String procedimento;

    @Schema(description = "Data do atendimento", example = "2026-05-20")
    @NotNull(message = "Data do prontuário é obrigatória")
    @PastOrPresent(message = "Data do prontuário não pode ser futura")
    private LocalDate dataProcedimento;

    @Schema(description = "Local do atendimento", example = "Clínica Clyvo - Unidade Paulista")
    @NotBlank(message = "Local é obrigatório")
    @Size(max = 120, message = "Local deve ter no máximo 120 caracteres")
    private String localAtendimento;

    @Schema(description = "ID do pet atendido", example = "1")
    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;

    @Schema(description = "ID do veterinário responsável", example = "2")
    @NotNull(message = "ID do veterinário é obrigatório")
    private Long veterinarioId;
}