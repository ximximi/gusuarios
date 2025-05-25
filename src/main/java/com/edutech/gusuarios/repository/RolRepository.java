package com.edutech.gusuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.gusuarios.model.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
    List<Rol> findAll();
    
    Optional<Rol> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
}