package com.edutech.gusuarios.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, unique = true, nullable = false)
    private String rut;

    @Column(length = 50, nullable = false)
    private String primerNomb;

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

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles = new ArrayList<>();
    //MÉTODO DE PAGO
    @Column(nullable = true)
    private String metodoPago;

    public Usuario() {
    }

    public Usuario(Long id, String rut, String primerNomb, String segundoNomb, String primerApell, String segundoApell, Date fechaNacimiento, String username, String email, String contrasena, EstadoUsuario estado, LocalDateTime fechaRegistro, List<Rol> roles, String metodoPago) {
        this.id = id;
        this.rut = rut;
        this.primerNomb = primerNomb;
        this.segundoNomb = segundoNomb;
        this.primerApell = primerApell;
        this.segundoApell = segundoApell;
        this.fechaNacimiento = fechaNacimiento;
        this.username = username;
        this.email = email;
        this.contrasena = contrasena;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.roles = roles;
        this.metodoPago = metodoPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getPrimerNomb() {
        return primerNomb;
    }

    public void setPrimerNomb(String primerNomb) {
        this.primerNomb = primerNomb;
    }

    public String getSegundoNomb() {
        return segundoNomb;
    }

    public void setSegundoNomb(String segundoNomb) {
        this.segundoNomb = segundoNomb;
    }

    public String getPrimerApell() {
        return primerApell;
    }

    public void setPrimerApell(String primerApell) {
        this.primerApell = primerApell;
    }

    public String getSegundoApell() {
        return segundoApell;
    }

    public void setSegundoApell(String segundoApell) {
        this.segundoApell = segundoApell;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    // Métodos de negocio
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

    // El fokin override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", rut='" + rut + '\'' +
                ", primerNomb='" + primerNomb + '\'' +
                ", segundoNomb='" + segundoNomb + '\'' +
                ", primerApell='" + primerApell + '\'' +
                ", segundoApell='" + segundoApell + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", estado=" + estado +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}

/* 
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
    private Date fechaRegistro;

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
*/