package br.fiap.joao565291.service;

import br.fiap.joao565291.domain.Bairro;
import br.fiap.joao565291.dto.BairroDTO;
import br.fiap.joao565291.exception.NaoEncontradoException;
import br.fiap.joao565291.exception.RegraNegocioException;
import br.fiap.joao565291.mapper.BairroMapper;
import br.fiap.joao565291.repository.BairroRepository;
import br.fiap.joao565291.repository.TrechoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BairroService {
    private final BairroRepository repo;
    private final TrechoRepository trechoRepo;
    private final BairroMapper mapper;

    public List<BairroDTO> listar() {
        return repo.findAllByOrderByNomeAsc()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public BairroDTO buscarPorId(Long id) {
        return mapper.toDTO(buscarEntidade(id));
    }

    public Bairro buscarEntidade(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Bairro " + id + " não encontrado."));
    }

    @Transactional
    public BairroDTO salvar(BairroDTO dto) {
        Bairro bairro;
        if (dto.getId() == null) {
            bairro = mapper.toEntity(dto);
        } else {
            bairro = buscarEntidade(dto.getId());
            bairro.setNome(dto.getNome());
            bairro.setZona(dto.getZona());
            bairro.setAreaKm2(dto.getAreaKm2());
        }
        return mapper.toDTO(repo.save(bairro));
    }

    // R2
    @Transactional
    public void excluir(Long id) {
        Bairro bairro = buscarEntidade(id);
        if (trechoRepo.existsByBairroId(id)) {
            throw new RegraNegocioException("O bairro " + bairro.getNome()
                    + " possui trechos cadastrados e não pode ser excluído.");
        }
        repo.delete(bairro);
    }
}
