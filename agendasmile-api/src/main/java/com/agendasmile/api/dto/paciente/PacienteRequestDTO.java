package com.agendasmile.api.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class PacienteRequestDTO {

    @NotBlank(message = "Nome do paciente é obrigatório!")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres!")
    private String nome;

    @NotBlank(message = "CPF do paciente é obrigatório!")
    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "E-mail do paciente é obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;

    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
