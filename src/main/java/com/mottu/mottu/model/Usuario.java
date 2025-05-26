package com.mottu.mottu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

//Nome da tabela no banco de dados
@Entity(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //gerar id automatico
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max=20, message = "O nome de usuário deve ter entre 3 a 20 caracteres")
    @NotNull
    private String username;

    @NotBlank(message = "A senha é obrigatório")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotNull
    private String password;

    @Email(message = "O e-mail deve ser válido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min= 9, max = 13, message="O telefone deve ter entre 9 a 12 digitos")
    @NotNull
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turno turno;

    public Usuario() {}

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelefone() {
        return telefone;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
