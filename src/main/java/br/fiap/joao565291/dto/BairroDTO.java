package br.fiap.joao565291.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BairroDTO {
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @NotBlank(message = "Selecione a zona")
    private String zona;

    @NotNull(message = "A área é obrigatória")
    @Positive(message = "A área deve ser maior que zero")
    private Double areaKm2;
}
