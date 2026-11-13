package br.fiap.joao565291.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PainelDTO {
    private String bairroNome;
    private long qtdNormal;
    private long qtdAtencao;
    private long qtdAlerta;
    private List<PainelItemDTO> itens;
}
