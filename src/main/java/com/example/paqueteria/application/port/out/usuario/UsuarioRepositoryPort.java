package com.example.paqueteria.application.port.out.usuario;

import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioCorreo;
import com.example.paqueteria.domain.valueobjects.UsuarioUsername;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositoryPort {

    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    List<Usuario> findAll();
    boolean delete(UUID id);
    Optional<Usuario> findByUsername(UsuarioUsername username);
    Usuario update(Usuario usuario);
    boolean existsByUsername(UsuarioUsername username);
    boolean existsByEmail(UsuarioCorreo correo);
}
