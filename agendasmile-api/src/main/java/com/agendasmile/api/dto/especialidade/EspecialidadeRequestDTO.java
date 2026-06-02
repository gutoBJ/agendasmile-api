package com.agendasmile.api.dto.especialidade;

import jakarta.validation.constraints.NotBlank;

public class EspecialidadeRequestDTO {

    @NotBlank(message = "Nome da especialidade é obrigatório!")
    private String nome;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
