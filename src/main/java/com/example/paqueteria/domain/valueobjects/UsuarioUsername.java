package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public final class UsuarioUsername {

    private final String valor;

    public UsuarioUsername(String valor) {
        String normalizado = normalizar(valor);
        validacion(normalizado);
        this.valor = normalizado;
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        return valor.trim().toLowerCase();
    }

    private void validacion(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (!valor.matches("^[a-z0-9_]{4,20}$")) {
            throw new IllegalArgumentException(
                    "El nombre de usuario debe tener entre 4 y 20 caracteres, solo letras minúsculas, números y guion bajo"
            );
        }
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioUsername that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}