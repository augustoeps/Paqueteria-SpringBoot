package com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record CrearUsuarioRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "El nombre de usuario es obligatorio")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        String contrasena,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        String correo
) {
}