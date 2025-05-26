package com.edutech.gusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDTO {
    private Long idUsuario;
    private String correo;
    // private String passwordHash; 
    private String estado;
    private RolRegistroDTO rol;
}