package com.edutech.gusuarios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.edutech.gusuarios.dto.UsuarioRegistroDTO;
import com.edutech.gusuarios.model.EstadoUsuario;
import com.edutech.gusuarios.model.Rol;
import com.edutech.gusuarios.model.Usuario;
import com.edutech.gusuarios.repository.UsuarioRepository;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;
// Dentro de la clase UsuarioService en ms-gusuarios

    public UsuarioRegistroDTO obtenerRegistroPorCorreo(String correo) {
    String urlMsRegistro = "http://localhost:8081/api/usuarios/buscar/" + correo;

    try {
        ResponseEntity<UsuarioRegistroDTO> response =
                restTemplate.getForEntity(urlMsRegistro, UsuarioRegistroDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.err.println("Respuesta no OK de REGISTRO: " + response.getStatusCode());
            return null;
        }
    } catch (HttpClientErrorException.NotFound ex) {
        System.err.println("Usuario con correo '" + correo + "' no encontrado en ms-registro.");
        return null; // El usuario no existe en el servicio de registro
    } catch (RestClientException ex) {
        // Error de conexión, timeout, o el servicio de registro no está disponible
        System.err.println("Error al llamar a ms-registro: " + ex.getMessage());
        return null;
    }
}

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> findByRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }
    
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
    
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public List<Usuario> findByEstado(EstadoUsuario estado) {
        return usuarioRepository.findByEstado(estado);
    }
    
    public Usuario save(Usuario usuario) {
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }
        return usuarioRepository.save(usuario);
    }
    
    
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }
    
    public boolean existsByRut(String rut) {
        return usuarioRepository.existsByRut(rut);
    }
    
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    public Usuario modificarInformacion(Long id, String primerNomb, String segundoNomb, String primerApell, String segundoApell, String email) {
        Optional<Usuario> usuarioData = usuarioRepository.findById(id);
        if (usuarioData.isPresent()) {
            Usuario usuario = usuarioData.get();
            usuario.modificarInformacion(primerNomb, segundoNomb, primerApell, segundoApell, email);
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    
    public boolean verificarCredenciales(String username, String contrasena) {
        Optional<Usuario> usuarioData = usuarioRepository.findByUsername(username);
        if (usuarioData.isPresent()) {
            return usuarioData.get().verificarCredenciales(username, contrasena);
        }
        return false;
    }
    
    public Usuario cambiarEstado(Long id, EstadoUsuario estado) {
        Optional<Usuario> usuarioData = usuarioRepository.findById(id);
        if (usuarioData.isPresent()) {
            Usuario usuario = usuarioData.get();
            usuario.cambiarEstado(estado);
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    
    public Usuario agregarRol(Long usuarioId, Rol rol) {
        Optional<Usuario> usuarioData = usuarioRepository.findById(usuarioId);
        if (usuarioData.isPresent()) {
            Usuario usuario = usuarioData.get();
            usuario.getRoles().add(rol);
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    
    public Usuario removerRol(Long usuarioId, Long rolId) {
        Optional<Usuario> usuarioData = usuarioRepository.findById(usuarioId);
        if (usuarioData.isPresent()) {
            Usuario usuario = usuarioData.get();
            usuario.setRoles(usuario.getRoles().stream()
                .filter(rol -> !rol.getId().equals(rolId))
                .toList());
            return usuarioRepository.save(usuario);
        }
        return null;
    }
}