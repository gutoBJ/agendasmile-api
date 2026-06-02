package com.agendasmile.api.repository;

import com.agendasmile.api.entity.DentistaEspecialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DentistaEspecialidadeRepository extends JpaRepository<DentistaEspecialidade, Long> {

    // Busca todas especialidades de um dentista
    List<DentistaEspecialidade> findAllByDentistaId(Long idDentista);

    // Remove todas especialidades de um dentista
    // (útil ao atualizar as especialidades)
    void deleteAllByDentistaId(Long idDentista);

    // Verifica se já existe o vínculo
    boolean existsByDentistaIdAndEspecialidadeId(Long idDentista, Long idEspecialidade);

}
