package com.agendasmile.api.repository;

import com.agendasmile.api.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    // Validação de duplicidade
    boolean existsByNome(String nome);

    // Busca por nome (útil para filtros)
    Optional<Especialidade> findByNome(String nome);

}
