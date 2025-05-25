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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.gusuarios.model.EstadoUsuario;
import com.edutech.gusuarios.model.Rol;
import com.edutech.gusuarios.model.Usuario;
import com.edutech.gusuarios.service.RolService;
import com.edutech.gusuarios.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioData = usuarioService.findById(id);
        if (usuarioData.isPresent()) {
            return new ResponseEntity<>(usuarioData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Usuario> getUsuarioByRut(@PathVariable String rut) {
        Optional<Usuario> usuarioData = usuarioService.findByRut(rut);
        if (usuarioData.isPresent()) {
            return new ResponseEntity<>(usuarioData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable String username) {
        Optional<Usuario> usuarioData = usuarioService.findByUsername(username);
        if (usuarioData.isPresent()) {
            return new ResponseEntity<>(usuarioData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Usuario>> getUsuariosByEstado(@PathVariable EstadoUsuario estado) {
        List<Usuario> usuarios = usuarioService.findByEstado(estado);
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        if (usuarioService.existsByRut(usuario.getRut())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (usuarioService.existsByUsername(usuario.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            Usuario savedUsuario = usuarioService.save(usuario);
            return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioData = usuarioService.findById(id);
        
        if (usuarioData.isPresent()) {
            // Verificar si el nuevo rut ya existe y no es el mismo que el actual
            if (!usuarioData.get().getRut().equals(usuario.getRut()) &&
                    usuarioService.existsByRut(usuario.getRut())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
            // Verificar si el nuevo username ya existe y no es el mismo que el actual
            if (!usuarioData.get().getUsername().equals(usuario.getUsername()) &&
                    usuarioService.existsByUsername(usuario.getUsername())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
            // Verificar si el nuevo email ya existe y no es el mismo que el actual
            if (!usuarioData.get().getEmail().equals(usuario.getEmail()) &&
                    usuarioService.existsByEmail(usuario.getEmail())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
            Usuario updatedUsuario = usuarioData.get();
            updatedUsuario.setRut(usuario.getRut());
            updatedUsuario.setPrimerNomb(usuario.getPrimerNomb());
            updatedUsuario.setSegundoNomb(usuario.getSegundoNomb());
            updatedUsuario.setPrimerApell(usuario.getPrimerApell());
            updatedUsuario.setSegundoApell(usuario.getSegundoApell());
            updatedUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
            updatedUsuario.setUsername(usuario.getUsername());
            updatedUsuario.setEmail(usuario.getEmail());
            updatedUsuario.setContrasena(usuario.getContrasena());
            updatedUsuario.setEstado(usuario.getEstado());
            
            return new ResponseEntity<>(usuarioService.save(updatedUsuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable Long id) {
        try {
            if (!usuarioService.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            usuarioService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}/modificar-informacion")
    public ResponseEntity<Usuario> modificarInformacion(
            @PathVariable Long id,
            @RequestParam String primerNomb,
            @RequestParam(required = false) String segundoNomb,
            @RequestParam String primerApell,
            @RequestParam(required = false) String segundoApell,
            @RequestParam String email) {
        
        // Verificar si el nuevo email ya existe y no es el mismo que el actual
        Optional<Usuario> usuarioExistente = usuarioService.findById(id);
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getEmail().equals(email) &&
                usuarioService.existsByEmail(email)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        Usuario updatedUsuario = usuarioService.modificarInformacion(id, primerNomb, segundoNomb, primerApell, segundoApell, email);
        if (updatedUsuario != null) {
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/verificar-credenciales")
    public ResponseEntity<Boolean> verificarCredenciales(
            @RequestParam String username,
            @RequestParam String contrasena) {
        boolean credencialesValidas = usuarioService.verificarCredenciales(username, contrasena);
        return new ResponseEntity<>(credencialesValidas, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/cambiar-estado/{estado}")
    public ResponseEntity<Usuario> cambiarEstado(
            @PathVariable Long id,
            @PathVariable EstadoUsuario estado) {
        Usuario updatedUsuario = usuarioService.cambiarEstado(id, estado);
        if (updatedUsuario != null) {
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{usuarioId}/roles/{rolId}")
    public ResponseEntity<Usuario> agregarRol(
            @PathVariable Long usuarioId,
            @PathVariable Long rolId) {
        Optional<Rol> rolData = rolService.findById(rolId);
        if (rolData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Usuario updatedUsuario = usuarioService.agregarRol(usuarioId, rolData.get());
        if (updatedUsuario != null) {
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{usuarioId}/roles/{rolId}")
    public ResponseEntity<Usuario> removerRol(
            @PathVariable Long usuarioId,
            @PathVariable Long rolId) {
        Usuario updatedUsuario = usuarioService.removerRol(usuarioId, rolId);
        if (updatedUsuario != null) {
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}