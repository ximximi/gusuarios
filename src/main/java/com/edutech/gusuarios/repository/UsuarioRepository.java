package com.edutech.gusuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.gusuarios.model.EstadoUsuario;
import com.edutech.gusuarios.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    List<Usuario> findAll();
    
    Optional<Usuario> findByRut(String rut);
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findByEstado(EstadoUsuario estado);
    
    boolean existsByRut(String rut);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}