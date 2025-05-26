package com.edutech.gusuarios.dto;


import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolRegistroDTO {
    private Long idRol;
    private String nombreRol;
    private String descripcion;
    private Set<String> permisos; // Los PermisoEnum de ms-registro se recibirán como String
}