package com.example.paqueteria.application.port.in.usuario;

import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.enums.UsuarioRol;
import com.example.paqueteria.domain.valueobjects.UsuarioContrasenaHash;

import java.util.UUID;

public interface ChangeRolUsuarioUseCase {
    Usuario changeRol(UUID usuarioId, UsuarioRol nuevoRol);
}
