package com.agendasmile.api.dto.especialidade;

public class EspecialidadeResponseDTO {

    private Long id;
    private String nome;

    public EspecialidadeResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
}
