package com.agendasmile.api.dto.usuario;

import com.agendasmile.api.enums.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class UsuarioRequestDTO {

    @NotBlank(message = "Nome do usuário é obrigatório!")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres!")
    private String nome;

    @NotBlank(message = "CPF do usuário é obrigatório!")
    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "E-mail do usuário é obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "Senha é obrigatória!")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres!")
    private String senha;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
