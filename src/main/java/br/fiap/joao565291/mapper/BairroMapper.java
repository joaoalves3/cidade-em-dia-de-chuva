package br.fiap.joao565291.mapper;

import br.fiap.joao565291.domain.Bairro;
import br.fiap.joao565291.dto.BairroDTO;
import org.springframework.stereotype.Component;

@Component
public class BairroMapper {
    public Bairro toEntity(BairroDTO dto) {
        Bairro bairro = new Bairro();
        bairro.setId(dto.getId());
        bairro.setNome(dto.getNome());
        bairro.setZona(dto.getZona());
        bairro.setAreaKm2(dto.getAreaKm2());
        return bairro;
    }

    public BairroDTO toDTO(Bairro bairro) {
        return new BairroDTO(
                bairro.getId(),
                bairro.getNome(),
                bairro.getZona(),
                bairro.getAreaKm2()
        );
    }
}
