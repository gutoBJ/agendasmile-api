package com.agendasmile.api.dto.auth;

import com.agendasmile.api.enums.PerfilUsuario;

public class LoginResponseDTO {

    private String token;
    private String nome;
    private String email;
    private PerfilUsuario perfil;

    public LoginResponseDTO(
            String token,
            String nome,
            String email,
            PerfilUsuario perfil) {
        this.token = token;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    public String getToken() { return token; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public PerfilUsuario getPerfil() { return perfil; }
}
