package com.example.paqueteria.application.port.in.usuario;

import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioUsername;

import java.util.Optional;

public interface FindByUsernameUsuarioUseCase {
    Optional<Usuario> findByUsername(UsuarioUsername username);
}