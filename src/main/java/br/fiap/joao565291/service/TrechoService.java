package br.fiap.joao565291.service;

import br.fiap.joao565291.domain.Bairro;
import br.fiap.joao565291.domain.Trecho;
import br.fiap.joao565291.dto.TrechoDTO;
import br.fiap.joao565291.exception.NaoEncontradoException;
import br.fiap.joao565291.mapper.TrechoMapper;
import br.fiap.joao565291.repository.LeituraRepository;
import br.fiap.joao565291.repository.TrechoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrechoService {
    private final TrechoRepository repo;
    private final LeituraRepository leituraRepo;
    private final BairroService bairroService;
    private final TrechoMapper mapper;

    public List<TrechoDTO> listar() {
        return repo.findAllByOrderByBairroNomeAscLogradouroAsc()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TrechoDTO buscarPorId(Long id) {
        return mapper.toDTO(buscarEntidade(id));
    }

    public Trecho buscarEntidade(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Trecho " + id + " não encontrado."));
    }

    @Transactional
    public TrechoDTO salvar(TrechoDTO dto) {
        Bairro bairro = bairroService.buscarEntidade(dto.getBairroId());
        Trecho trecho;
        if (dto.getId() == null) {
            trecho = mapper.toEntity(dto, bairro);
        } else {
            trecho = buscarEntidade(dto.getId());
            trecho.setLogradouro(dto.getLogradouro());
            trecho.setTipoCalcada(dto.getTipoCalcada());
            trecho.setAltitude(dto.getAltitude());
            trecho.setBairro(bairro);
        }
        return mapper.toDTO(repo.save(trecho));
    }

    // R3 - P5 = 1: apaga as leituras do trecho e depois o trecho
    @Transactional
    public void excluir(Long id) {
        Trecho trecho = buscarEntidade(id);
        if (leituraRepo.existsByTrechoId(id)) {
            leituraRepo.deleteByTrechoId(id);
        }
        repo.delete(trecho);
    }
}
