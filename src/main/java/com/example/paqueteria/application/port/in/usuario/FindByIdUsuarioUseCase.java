package com.example.paqueteria.application.port.in.usuario;

import com.example.paqueteria.domain.entity.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface FindByIdUsuarioUseCase {

    Optional<Usuario> findById(UUID id);
}
