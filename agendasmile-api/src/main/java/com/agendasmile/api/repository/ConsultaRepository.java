package com.agendasmile.api.repository;

import com.agendasmile.api.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Regra 1 — conflito de horário do dentista
    boolean existsByDentistaIdAndDataInicioLessThanAndDataFimGreaterThan(
            Long idDentista,
            LocalDateTime dataFim,
            LocalDateTime dataInicio
    );

    boolean existsByPacienteId(Long idPaciente);

    // Regra 6 — dentista vê só suas consultas
    List<Consulta> findAllByDentistaId(Long idDentista);

    // Listar por paciente
    List<Consulta> findAllByPacienteId(Long idPaciente);

}
