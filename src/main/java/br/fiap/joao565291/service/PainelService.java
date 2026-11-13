package br.fiap.joao565291.service;

import br.fiap.joao565291.domain.Bairro;
import br.fiap.joao565291.domain.Leitura;
import br.fiap.joao565291.domain.Trecho;
import br.fiap.joao565291.dto.PainelDTO;
import br.fiap.joao565291.dto.PainelItemDTO;
import br.fiap.joao565291.repository.LeituraRepository;
import br.fiap.joao565291.repository.TrechoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PainelService {
    private final BairroService bairroService;
    private final TrechoRepository trechoRepo;
    private final LeituraRepository leituraRepo;

    public PainelDTO montarPainel(Long bairroId) {
        Bairro bairro = bairroService.buscarEntidade(bairroId);

        List<PainelItemDTO> itens = trechoRepo.findByBairroIdOrderByLogradouroAsc(bairroId)
                .stream()
                .map(this::montarItem)
                .toList();

        long qtdNormal = itens.stream().filter(i -> "NORMAL".equals(i.getUltimoStatus())).count();
        long qtdAtencao = itens.stream().filter(i -> "ATENCAO".equals(i.getUltimoStatus())).count();
        long qtdAlerta = itens.stream().filter(i -> "ALERTA".equals(i.getUltimoStatus())).count();

        return new PainelDTO(bairro.getNome(), qtdNormal, qtdAtencao, qtdAlerta, itens);
    }

    private PainelItemDTO montarItem(Trecho trecho) {
        Optional<Leitura> ultima = leituraRepo.findFirstByTrechoIdOrderByDataHoraDesc(trecho.getId());
        return new PainelItemDTO(
                trecho.getLogradouro(),
                trecho.getTipoCalcada(),
                trecho.getAltitude(),
                ultima.map(Leitura::getLaminaCm).orElse(null),
                ultima.map(Leitura::getStatus).orElse(null)
        );
    }
}
