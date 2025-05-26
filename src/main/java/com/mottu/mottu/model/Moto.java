package com.mottu.mottu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

@Entity(name = "moto")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Moto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O chassi não pode estar em branco")
    @Size(min = 3, max = 8, message = "O chassi deve ter entre 3 e 8 caracteres")
    private String chassi;

    @NotBlank(message = "A placa não pode estar em branco")
    @Size(min = 7, max = 8, message = "A placa deve ter entre 7 e 8 caracteres")
    private String placa;

    @NotBlank(message = "O código de registro não pode estar em branco")
    private String codigoRegistro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Galpao galpao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modelo modelo;

    public Moto() {}

    public Long getId() {
        return id;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public Galpao getGalpao() {
        return galpao;
    }

    public void setGalpao(Galpao galpao) {
        this.galpao = galpao;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}
