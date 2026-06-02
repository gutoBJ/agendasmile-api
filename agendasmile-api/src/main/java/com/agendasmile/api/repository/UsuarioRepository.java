package com.agendasmile.api.repository;

import com.agendasmile.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interface extende de Interface
// Interface herda de Interface

// O Spring já reconhece automaticamente como Repository
// por causa do JpaRepository — o @Repository é redundante
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}