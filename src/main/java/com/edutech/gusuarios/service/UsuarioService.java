package com.edutech.gusuarios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gusuarios.model.EstadoUsuario;
import com.edutech.gusuarios.model.Rol;
import com.edutech.gusuarios.model.Usuario;
import com.edutech.gusuarios.repository.UsuarioRepository;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

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