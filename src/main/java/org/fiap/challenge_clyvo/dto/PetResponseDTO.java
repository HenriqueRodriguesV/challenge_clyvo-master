package org.fiap.challenge_clyvo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "DTO de saída com dados do pet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetResponseDTO {
    @Schema(description = "ID do pet", example = "1")
    private Long id;

    @Schema(description = "Nome do pet", example = "Thor")
    private String nome;

    @Schema(description = "Descrição do pet", example = "Cachorro dócil e vacinado")
    private String descricao;

    @Schema(description = "Raça do pet", example = "Golden Retriever")
    private String raca;

    @Schema(description = "Data de nascimento do pet", example = "2021-03-15")
    private LocalDate dataNascimento;

    @Schema(description = "ID do responsável", example = "1")
    private Long responsavelId;

    @Schema(description = "Nome do responsável", example = "Maria Silva")
    private String nomeResponsavel;
}