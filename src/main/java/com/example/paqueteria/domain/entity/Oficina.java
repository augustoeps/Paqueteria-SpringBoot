package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.valueobjects.OficinaCodigo;
import com.example.paqueteria.domain.valueobjects.OficinaDireccion;
import com.example.paqueteria.domain.valueobjects.OficinaNombre;

import java.util.Objects;
import java.util.UUID;

public class Oficina {

    private final UUID id;
    private OficinaCodigo codigo;
    private OficinaNombre nombre;
    private OficinaDireccion direccion;
    private final UUID provinciaId;

    public Oficina(OficinaCodigo codigo, OficinaNombre nombre, OficinaDireccion direccion, UUID provinciaId) {
        this.id = UUID.randomUUID();
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.provinciaId = provinciaId;
    }

    public Oficina(UUID id, OficinaCodigo codigo, OficinaNombre nombre, OficinaDireccion direccion, UUID provinciaId) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.codigo = Objects.requireNonNull(codigo, "El código es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        this.direccion = Objects.requireNonNull(direccion, "La dirección es obligatoria");
        this.provinciaId = Objects.requireNonNull(provinciaId, "La provincia es obligatoria");
    }

    public void cambiarNombre(OficinaNombre nuevoNombre) {
        this.nombre = Objects.requireNonNull(nuevoNombre, "El nombre es obligatorio");
    }

    public void cambiarDireccion(OficinaDireccion nuevaDireccion) {
        this.direccion = Objects.requireNonNull(nuevaDireccion, "La dirección es obligatoria");
    }

    public UUID getId() { return id; }
    public OficinaCodigo getCodigo() { return codigo; }
    public OficinaNombre getNombre() { return nombre; }
    public OficinaDireccion getDireccion() { return direccion; }
    public UUID getProvinciaId() { return provinciaId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Oficina otra)) return false;
        return id.equals(otra.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}