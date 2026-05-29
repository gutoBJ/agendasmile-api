package com.agendasmile.api.repository;

import com.agendasmile.api.entity.DentistaEspecialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaEspecialidadeRepository extends JpaRepository<DentistaEspecialidade, Long> {
}
