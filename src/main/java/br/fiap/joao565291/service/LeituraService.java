package br.fiap.joao565291.service;

import br.fiap.joao565291.domain.Leitura;
import br.fiap.joao565291.domain.Trecho;
import br.fiap.joao565291.dto.LeituraDTO;
import br.fiap.joao565291.exception.NaoEncontradoException;
import br.fiap.joao565291.mapper.LeituraMapper;
import br.fiap.joao565291.repository.LeituraRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LeituraService {
    // P2: L = 10 + 2 * (1691316 % 4) = 10
    private static final int LIMIAR = 10;

    private final LeituraRepository repo;
    private final TrechoService trechoService;
    private final LeituraMapper mapper;

    // R1
    public String calcularStatus(int laminaCm) {
        if (laminaCm >= LIMIAR) {
            return "ALERTA";
        }
        if (laminaCm >= LIMIAR / 2) {
            return "ATENCAO";
        }
        return "NORMAL";
    }

    public List<LeituraDTO> listar() {
        return repo.findAllByOrderByDataHoraDesc()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public LeituraDTO buscarPorId(Long id) {
        return mapper.toDTO(buscarEntidade(id));
    }

    public Leitura buscarEntidade(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Leitura " + id + " não encontrada."));
    }

    @Transactional
    public LeituraDTO salvar(LeituraDTO dto) {
        Trecho trecho = trechoService.buscarEntidade(dto.getTrechoId());
        String status = calcularStatus(dto.getLaminaCm());
        Leitura leitura;
        if (dto.getId() == null) {
            leitura = mapper.toEntity(dto, trecho, status);
        } else {
            leitura = buscarEntidade(dto.getId());
            leitura.setDataHora(dto.getDataHora());
            leitura.setLaminaCm(dto.getLaminaCm());
            leitura.setStatus(status);
            leitura.setTrecho(trecho);
        }
        return mapper.toDTO(repo.save(leitura));
    }

    @Transactional
    public void excluir(Long id) {
        Leitura leitura = buscarEntidade(id);
        repo.delete(leitura);
    }
}
