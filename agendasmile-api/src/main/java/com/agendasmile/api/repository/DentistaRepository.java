package com.agendasmile.api.repository;

import com.agendasmile.api.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {

    // Validação de duplicidade
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCro(String cro);

    // Validação na atualização
    boolean existsByCpfAndIdNot(String cpf, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByCroAndIdNot(String cro, Long id);

    // Listar só os ativos
    List<Dentista> findAllByAtivoTrue();

}
