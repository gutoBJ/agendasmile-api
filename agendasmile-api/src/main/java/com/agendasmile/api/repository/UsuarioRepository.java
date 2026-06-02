package com.agendasmile.api.repository;

import com.agendasmile.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Interface extende de Interface
// Interface herda de Interface

// O Spring já reconhece automaticamente como Repository
// por causa do JpaRepository — o @Repository é redundante

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Login — busca por email
    Optional<Usuario> findByEmail(String email);

    // Validação de duplicidade no cadastro
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    // Validação de duplicidade na atualização
    // (ignora o próprio registro)
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByCpfAndIdNot(String cpf, Long id);

}