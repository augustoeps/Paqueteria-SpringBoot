package com.example.paqueteria.domain.valueobjects;

import java.util.Objects;

public class UsuarioApellido{

        private final String apellido;

        public UsuarioApellido(String nombre) {
            String nombreNormalizado = normalizar(nombre);
            validacion(nombreNormalizado);
            this.apellido = nombreNormalizado;
        }

        private String normalizar(String apellido) {
            if (apellido == null) {
                return null;
            }
            return apellido.trim();
        }

        private void validacion(String apellido) {
            if (apellido == null || apellido.isBlank()) {
                throw new IllegalArgumentException("El apellido es obligatorio");
            }
            if (apellido.length() < 2) {
                throw new IllegalArgumentException("El apellido debe tener al menos 2 caracteres");
            }
        }

        public String getApellido() {
            return apellido;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UsuarioApellido that)) return false;
            return Objects.equals(apellido, that.apellido);
        }

        @Override
        public int hashCode() {
            return Objects.hash(apellido);
        }
    }


