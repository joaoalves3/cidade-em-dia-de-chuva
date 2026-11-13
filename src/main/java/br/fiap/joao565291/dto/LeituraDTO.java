package br.fiap.joao565291.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraDTO {
    private Long id;

    @NotNull(message = "Selecione o trecho")
    private Long trechoId;

    @NotNull(message = "A data e hora é obrigatória")
    @PastOrPresent(message = "A data e hora não pode estar no futuro")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHora;

    @NotNull(message = "A lâmina é obrigatória")
    @Min(value = 0, message = "A lâmina deve estar entre 0 e 300 cm")
    @Max(value = 300, message = "A lâmina deve estar entre 0 e 300 cm")
    private Integer laminaCm;

    private String status;

    private String trechoDescricao;
}
