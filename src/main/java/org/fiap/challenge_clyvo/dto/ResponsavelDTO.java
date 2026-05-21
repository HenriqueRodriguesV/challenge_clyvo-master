package org.fiap.challenge_clyvo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "DTO de responsável")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponsavelDTO {
    @Schema(description = "ID do responsável", example = "1")
    private Long id;

    @Schema(description = "Nome do responsável", example = "Maria Silva")
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Schema(description = "Email do responsável", example = "maria@email.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 120, message = "Email deve ter no máximo 120 caracteres")
    private String email;

    @Schema(description = "CPF do responsável, somente números", example = "12345678901")
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @Schema(description = "Data de nascimento do responsável", example = "1995-08-10")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;
}