package br.fiap.joao565291.mapper;

import br.fiap.joao565291.domain.Bairro;
import br.fiap.joao565291.domain.Trecho;
import br.fiap.joao565291.dto.TrechoDTO;
import org.springframework.stereotype.Component;

@Component
public class TrechoMapper {
    public Trecho toEntity(TrechoDTO dto, Bairro bairro) {
        Trecho trecho = new Trecho();
        trecho.setId(dto.getId());
        trecho.setLogradouro(dto.getLogradouro());
        trecho.setTipoCalcada(dto.getTipoCalcada());
        trecho.setAltitude(dto.getAltitude());
        trecho.setBairro(bairro);
        return trecho;
    }

    public TrechoDTO toDTO(Trecho trecho) {
        return new TrechoDTO(
                trecho.getId(),
                trecho.getLogradouro(),
                trecho.getTipoCalcada(),
                trecho.getAltitude(),
                trecho.getBairro().getId(),
                trecho.getBairro().getNome()
        );
    }
}
