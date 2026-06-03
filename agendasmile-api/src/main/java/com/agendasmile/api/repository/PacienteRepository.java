package com.agendasmile.api.repository;

import com.agendasmile.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Validação de duplicidade
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    // Validação na atualização
    boolean existsByCpfAndIdNot(String cpf, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);

    List<Paciente> findAllByAtivoTrue();

}
