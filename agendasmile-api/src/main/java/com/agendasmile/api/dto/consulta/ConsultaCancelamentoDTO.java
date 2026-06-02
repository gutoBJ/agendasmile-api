package com.agendasmile.api.dto.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConsultaCancelamentoDTO {

    @NotNull(message = "ID da consulta é obrigatório")
    private Long idConsulta;

    @NotBlank(message = "Motivo do cancelamento é obrigatório")
    private String motivoCancelamento;

    // Getters e Setters
    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivo) {
        this.motivoCancelamento = motivo;
    }
}