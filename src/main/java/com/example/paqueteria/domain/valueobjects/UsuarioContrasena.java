package com.example.paqueteria.domain.valueobjects;

import java.util.regex.Pattern;

public final class UsuarioContrasena {

    private static final Pattern TIENE_MAYUSCULA = Pattern.compile(".*[A-Z].*");
    private static final Pattern TIENE_NUMERO = Pattern.compile(".*\\d.*");

    private final String valor;

    public UsuarioContrasena(String valor) {
        validacion(valor);
        this.valor = valor;
    }

    private void validacion(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (valor.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        if (!TIENE_MAYUSCULA.matcher(valor).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener al menos una mayúscula");
        }
        if (!TIENE_NUMERO.matcher(valor).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener al menos un número");
        }
    }

    public String getValor() {
        return valor;
    }

    // Nota: NO tiene equals/hashCode/toString con el valor expuesto,
    // por seguridad, para evitar que la contraseña aparezca en logs accidentalmente
    @Override
    public String toString() {
        return "Contrasena[PROTEGIDA]";
    }
}