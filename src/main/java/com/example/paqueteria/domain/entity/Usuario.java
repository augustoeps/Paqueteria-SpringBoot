package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.enums.UsuarioRol;
import com.example.paqueteria.domain.valueobjects.*;

import java.util.Objects;
import java.util.UUID;

public class Usuario {

    private final UUID id;
    private final UsuarioNombre usuarioNombre;
    private final UsuarioApellido usuarioApellido;
    private final UsuarioUsername usuarioUsername;
    private UsuarioContrasenaHash usuarioContrasenaHash;
    private final UsuarioCorreo usuarioCorreo;
    private UsuarioRol usuarioRol;

    public Usuario(UsuarioNombre usuarioNombre, UsuarioApellido usuarioApellido, UsuarioUsername usuarioUsername,
                   UsuarioContrasenaHash usuarioContrasenaHash, UsuarioCorreo usuarioCorreo, UsuarioRol usuarioRol) {
        validacion(usuarioNombre, usuarioApellido, usuarioUsername, usuarioContrasenaHash, usuarioCorreo, usuarioRol);

        this.id = UUID.randomUUID();
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.usuarioUsername = usuarioUsername;
        this.usuarioContrasenaHash = usuarioContrasenaHash;
        this.usuarioCorreo = usuarioCorreo;
        this.usuarioRol = usuarioRol;
    }

    public Usuario(UUID id, UsuarioNombre usuarioNombre, UsuarioApellido usuarioApellido, UsuarioUsername usuarioUsername,
                   UsuarioContrasenaHash usuarioContrasenaHash, UsuarioCorreo usuarioCorreo, UsuarioRol usuarioRol) {
        Objects.requireNonNull(id, "El id es obligatorio");
        validacion(usuarioNombre, usuarioApellido, usuarioUsername, usuarioContrasenaHash, usuarioCorreo, usuarioRol);

        this.id = id;
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.usuarioUsername = usuarioUsername;
        this.usuarioContrasenaHash = usuarioContrasenaHash;
        this.usuarioCorreo = usuarioCorreo;
        this.usuarioRol = usuarioRol;
    }

    private void validacion(UsuarioNombre nombre, UsuarioApellido apellido, UsuarioUsername username,
                            UsuarioContrasenaHash contrasenaHash, UsuarioCorreo correo, UsuarioRol rol) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (apellido == null) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (username == null) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (contrasenaHash == null) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (correo == null) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
    }

    // --- Comportamiento de negocio ---

    public void cambiarContrasena(UsuarioContrasenaHash nuevaContrasenaHash) {
        this.usuarioContrasenaHash = Objects.requireNonNull(nuevaContrasenaHash, "La contraseña es obligatoria");
    }

    public void cambiarRol(UsuarioRol nuevoRol) {
        this.usuarioRol = Objects.requireNonNull(nuevoRol, "El rol es obligatorio");
    }

    // --- Getters ---

    public UUID getId() { return id; }
    public UsuarioNombre getUsuarioNombre() { return usuarioNombre; }
    public UsuarioApellido getUsuarioApellido() { return usuarioApellido; }
    public UsuarioUsername getUsuarioUsername() { return usuarioUsername; }
    public UsuarioContrasenaHash getUsuarioContrasenaHash() { return usuarioContrasenaHash; }
    public UsuarioCorreo getUsuarioCorreo() { return usuarioCorreo; }
    public UsuarioRol getUsuarioRol() { return usuarioRol; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}