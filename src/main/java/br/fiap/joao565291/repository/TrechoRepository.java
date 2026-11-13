package br.fiap.joao565291.repository;

import br.fiap.joao565291.domain.Trecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrechoRepository extends JpaRepository<Trecho, Long> {
    // P4 = 2
    List<Trecho> findAllByOrderByBairroNomeAscLogradouroAsc();
    List<Trecho> findByBairroIdOrderByLogradouroAsc(Long bairroId);
    boolean existsByBairroId(Long bairroId);
}
