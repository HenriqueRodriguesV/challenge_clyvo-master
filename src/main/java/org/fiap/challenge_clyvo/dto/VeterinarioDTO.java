package org.fiap.challenge_clyvo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "DTO de veterinário")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VeterinarioDTO {
    @Schema(description = "ID do veterinário", example = "2")
    private Long id;

    @Schema(description = "Nome do veterinário", example = "Dr. Carlos Souza")
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Schema(description = "Email do veterinário", example = "carlos@clinica.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 120, message = "Email deve ter no máximo 120 caracteres")
    private String email;

    @Schema(description = "CPF do veterinário, somente números", example = "98765432100")
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @Schema(description = "CRMV do veterinário", example = "SP-12345")
    @NotBlank(message = "CRMV é obrigatório")
    @Size(max = 20, message = "CRMV deve ter no máximo 20 caracteres")
    private String crmv;
}