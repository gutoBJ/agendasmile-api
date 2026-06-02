package com.agendasmile.api.dto.dentista;

import com.agendasmile.api.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public class DentistaRequestDTO {

    @NotBlank(message = "Nome do dentista é obrigatório!")
    private String nome;

    @NotBlank(message = "CPF do dentista é obrigatório!")
    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "E-mail do dentista é obrigatório!")
    @Email(message = "E-mail inválido!")
    private String email;

    @NotBlank(message = "CRO do dentista é obrigatório!")
    private String cro;

    @NotNull(message = "Usuário é obrigatório!")
    private Long idUsuario;

    @NotEmpty(message = "Dentista deve ter pelo menos uma especialidade!")
    private List<Long> especialidades;

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

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Long> getEspecialidades() { return especialidades; }

    public void setEspecialidades(List<Long> especialidades) {
        this.especialidades = especialidades;
    }
}
