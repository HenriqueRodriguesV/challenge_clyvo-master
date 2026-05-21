package org.fiap.challenge_clyvo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "DTO de entrada para cadastro e atualização de pet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetRequestDTO {
    @Schema(description = "Nome do pet", example = "Thor")
    @NotBlank(message = "Nome do pet é obrigatório")
    @Size(max = 80, message = "Nome do pet deve ter no máximo 80 caracteres")
    private String nome;

    @Schema(description = "Descrição do pet", example = "Cachorro dócil e vacinado")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @Schema(description = "Raça do pet", example = "Golden Retriever")
    @NotBlank(message = "Raça é obrigatória")
    @Size(max = 80, message = "Raça deve ter no máximo 80 caracteres")
    private String raca;

    @Schema(description = "Data de nascimento do pet", example = "2021-03-15")
    @NotNull(message = "Data de nascimento é obrigatória")
    @PastOrPresent(message = "Data de nascimento não pode ser futura")
    private LocalDate dataNascimento;

    @Schema(description = "ID do responsável pelo pet", example = "1")
    @NotNull(message = "ID do responsável é obrigatório")
    private Long responsavelId;
}