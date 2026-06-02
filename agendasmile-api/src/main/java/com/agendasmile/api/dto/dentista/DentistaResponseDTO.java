package com.agendasmile.api.dto.dentista;

import com.agendasmile.api.dto.especialidade.EspecialidadeResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class DentistaResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String cro;
    private LocalDateTime dataCriacao;
    private boolean ativo;
    private Long idUsuario;

    private List<EspecialidadeResponseDTO> especialidades;

    public DentistaResponseDTO(
            Long id,
            String nome,
            String cpf,
            String email,
            String cro,
            LocalDateTime dataCriacao,
            boolean ativo,
            Long idUsuario,
            List<EspecialidadeResponseDTO> especialidades)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.cro = cro;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
        this.idUsuario = idUsuario;
        this.especialidades = especialidades;
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

    public String getCro() {
        return cro;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public List<EspecialidadeResponseDTO> getEspecialidades() {
        return especialidades;
    }
}
