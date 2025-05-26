package com.edutech.gusuarios.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, unique = true, nullable = false)
    private String rut;

    @Column(length = 50, nullable = false)
    public String primerNomb;

    @Column(length = 50, nullable = true)
    private String segundoNomb;

    @Column(length = 50, nullable = false)
    private String primerApell;

    @Column(length = 50, nullable = true)
    private String segundoApell;   

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaNacimiento;
    
    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles = new ArrayList<>();

    public void modificarInformacion(String primerNomb, String segundoNomb, String primerApell, String segundoApell, String email) {
        this.primerNomb = primerNomb;
        this.segundoNomb = segundoNomb;
        this.primerApell = primerApell;
        this.segundoApell = segundoApell;
        this.email = email;
    }

    public boolean verificarCredenciales(String username, String contrasena) {
        return this.username.equals(username) && this.contrasena.equals(contrasena);
    }

    public void cambiarEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
}
