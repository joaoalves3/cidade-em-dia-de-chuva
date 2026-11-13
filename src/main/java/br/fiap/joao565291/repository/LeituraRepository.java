package br.fiap.joao565291.repository;

import br.fiap.joao565291.domain.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {
    List<Leitura> findAllByOrderByDataHoraDesc();
    Optional<Leitura> findFirstByTrechoIdOrderByDataHoraDesc(Long trechoId);
    boolean existsByTrechoId(Long trechoId);
    // P5 = 1 (cascata)
    void deleteByTrechoId(Long trechoId);
}
