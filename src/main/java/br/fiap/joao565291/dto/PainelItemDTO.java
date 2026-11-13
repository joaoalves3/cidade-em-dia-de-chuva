package br.fiap.joao565291.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PainelItemDTO {
    private String logradouro;
    private String tipoCalcada;
    private Integer altitude;
    private Integer ultimaLamina;
    private String ultimoStatus;
}
