package com.agendasmile.api.dto.usuario;

import com.agendasmile.api.enums.PerfilUsuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimoLogin;
    private PerfilUsuario perfil;
    private boolean ativo;

    public UsuarioResponseDTO(
            Long id,
            String nome,
            String cpf,
            String email,
            LocalDateTime dataCriacao,
            LocalDateTime ultimoLogin,
            PerfilUsuario perfil,
            boolean ativo)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.ultimoLogin = ultimoLogin;
        this.perfil = perfil;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
