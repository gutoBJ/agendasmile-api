package com.agendasmile.api.dto.paciente;

import java.time.LocalDateTime;

public class PacienteResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDateTime dataCriacao;
    private String telefone;
    private boolean ativo;

    public PacienteResponseDTO(
            Long id,
            String nome,
            String cpf,
            String email,
            LocalDateTime dataCriacao,
            String telefone,
            boolean ativo)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.telefone = telefone;
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

    public String getTelefone() {
        return telefone;
    }

    public boolean isAtivo() { return ativo; }
}
