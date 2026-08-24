package com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto;

import com.example.paqueteria.domain.entity.Usuario;

import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nombre,
        String apellido,
        String username,
        String correo,
        String rol
) {
    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsuarioNombre().getNombre(),
                usuario.getUsuarioApellido().getApellido(),
                usuario.getUsuarioUsername().getValor(),
                usuario.getUsuarioCorreo().getValor(),
                usuario.getUsuarioRol().name()
        );
    }
}