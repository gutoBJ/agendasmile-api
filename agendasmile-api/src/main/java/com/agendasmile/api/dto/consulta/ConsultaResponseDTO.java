package com.agendasmile.api.dto.consulta;

import com.agendasmile.api.enums.StatusConsulta;

import java.time.LocalDateTime;

public class ConsultaResponseDTO {

    private Long id;
    private Long idPaciente;
    private String nomePaciente;
    private Long idDentista;
    private String nomeDentista;
    private Long idUsuario;
    private String descricao;
    private String motivoCancelamento;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime dataRegistro;
    private StatusConsulta status;

    // Constructor (facilita criação no Service)
    public ConsultaResponseDTO(
            Long id,
            Long idPaciente,
            String nomePaciente,
            Long idDentista,
            String nomeDentista,
            Long idUsuario,
            String descricao,
            String motivoCancelamento,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            LocalDateTime dataRegistro,
            StatusConsulta status
    ) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.idDentista = idDentista;
        this.nomeDentista = nomeDentista;
        this.idUsuario = idUsuario;
        this.descricao = descricao;
        this.motivoCancelamento = motivoCancelamento;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataRegistro = dataRegistro;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public String getNomePaciente() { return nomePaciente; }

    public Long getIdDentista() {
        return idDentista;
    }

    public String getNomeDentista() {
        return nomeDentista;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public StatusConsulta getStatus() {
        return status;
    }
}