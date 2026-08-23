package com.example.paqueteria.application.port.in.usuario;

import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.enums.UsuarioRol;
import com.example.paqueteria.domain.valueobjects.*;

public interface CreateUsuarioUseCase {
    Usuario create(UsuarioNombre usuarioNombre, UsuarioApellido usuarioApellido, UsuarioUsername usuarioUsername,
                   UsuarioContrasena usuarioContrasena, UsuarioCorreo usuarioCorreo, UsuarioRol usuarioRol);
}
