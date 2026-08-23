package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

public final class UsuarioCorreo {

    private static final Pattern PATRON_EMAIL =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final String valor;

    public UsuarioCorreo(String valor) {
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
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (!PATRON_EMAIL.matcher(valor).matches()) {
            throw new IllegalArgumentException("El correo no tiene un formato válido");
        }
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioCorreo that)) return false;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}