package com.example.paqueteria.domain.valueobjects;

public final class UsuarioContrasenaHash {

    private final String hash;

    public UsuarioContrasenaHash(String hash) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("El hash de la contraseña es obligatorio");
        }
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "ContrasenaHash[PROTEGIDA]";
    }
}