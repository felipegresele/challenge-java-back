package com.mottu.mottu.dto;

import com.mottu.mottu.model.Turno;
import com.mottu.mottu.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max=20, message = "O nome de usuário deve ter entre 3 a 20 caracteres")
    @NotNull
    private String username;

    @NotBlank(message = "A senha é obrigatório")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotNull
    private String password;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min= 9, max = 13, message="O telefone deve ter entre 9 a 12 digitos")
    @NotNull
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turno turno;

    public UsuarioDTO () {

    }

    public UsuarioDTO(String password, String username, String email, String telefone, Turno turno) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.telefone = telefone;
        this.turno = turno;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", turno=" + turno +
                '}';
    }
}
