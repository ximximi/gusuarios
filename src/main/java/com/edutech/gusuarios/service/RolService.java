package com.edutech.gusuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.gusuarios.model.Permisos;
import com.edutech.gusuarios.model.Rol;
import com.edutech.gusuarios.repository.RolRepository;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
    
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }
    
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
    
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }
    
    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return rolRepository.existsById(id);
    }
    
    public boolean existsByNombre(String nombre) {
        return rolRepository.existsByNombre(nombre);
    }
    
    public Rol agregarPermiso(Long rolId, Permisos permiso) {
        Optional<Rol> rolData = rolRepository.findById(rolId);
        if (rolData.isPresent()) {
            Rol rol = rolData.get();
            rol.agregarPermiso(permiso);
            return rolRepository.save(rol);
        }
        return null;
    }
    
    public Rol removerPermiso(Long rolId, Permisos permiso) {
        Optional<Rol> rolData = rolRepository.findById(rolId);
        if (rolData.isPresent()) {
            Rol rol = rolData.get();
            rol.removerPermiso(permiso);
            return rolRepository.save(rol);
        }
        return null;
    }
}
