package com.edutech.gusuarios.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.gusuarios.model.Permisos;
import com.edutech.gusuarios.model.Rol;
import com.edutech.gusuarios.service.RolService;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> getRoles() {
        List<Rol> roles = rolService.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        Optional<Rol> rolData = rolService.findById(id);
        if (rolData.isPresent()) {
            return new ResponseEntity<>(rolData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        if (rolService.existsByNombre(rol.getNombre())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            Rol savedRol = rolService.save(rol);
            return new ResponseEntity<>(savedRol, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        Optional<Rol> rolData = rolService.findById(id);
        
        if (rolData.isPresent()) {
            // Verificar si el nuevo nombre ya existe y no es el mismo que el actual
            if (!rolData.get().getNombre().equals(rol.getNombre()) &&
                    rolService.existsByNombre(rol.getNombre())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
            Rol updatedRol = rolData.get();
            updatedRol.setNombre(rol.getNombre());
            updatedRol.setDescripcion(rol.getDescripcion());
            updatedRol.setPermisos(rol.getPermisos());
            
            return new ResponseEntity<>(rolService.save(updatedRol), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRol(@PathVariable Long id) {
        try {
            if (!rolService.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            rolService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/{id}/permisos/{permiso}")
    public ResponseEntity<Rol> addPermiso(@PathVariable Long id, @PathVariable Permisos permiso) {
        Rol updatedRol = rolService.agregarPermiso(id, permiso);
        if (updatedRol != null) {
            return new ResponseEntity<>(updatedRol, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}/permisos/{permiso}")
    public ResponseEntity<Rol> removePermiso(@PathVariable Long id, @PathVariable Permisos permiso) {
        Rol updatedRol = rolService.removerPermiso(id, permiso);
        if (updatedRol != null) {
            return new ResponseEntity<>(updatedRol, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}