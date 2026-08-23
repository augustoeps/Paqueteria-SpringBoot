package com.example.paqueteria.application.port.in.usuario;

import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioContrasena;
import com.example.paqueteria.domain.valueobjects.UsuarioContrasenaHash;

import java.util.UUID;

public interface ChangePasswordUsuarioUseCase {
    Usuario changePassword(UUID usuarioId, UsuarioContrasena nuevaContrasena);
}
