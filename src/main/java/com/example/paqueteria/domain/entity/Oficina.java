package com.example.paqueteria.domain.entity;

import com.example.paqueteria.domain.valueobjects.OficinaCodigo;
import com.example.paqueteria.domain.valueobjects.OficinaDireccion;
import com.example.paqueteria.domain.valueobjects.OficinaLatitud;
import com.example.paqueteria.domain.valueobjects.OficinaLongitud;
import com.example.paqueteria.domain.valueobjects.OficinaNombre;

import java.util.Objects;
import java.util.UUID;

public class Oficina {

    private final UUID id;
    private OficinaCodigo codigo;
    private OficinaNombre nombre;
    private OficinaDireccion direccion;
    private OficinaLatitud latitud;
    private OficinaLongitud longitud;

    private final UUID provinciaId;

    public Oficina(OficinaCodigo codigo, OficinaNombre nombre, OficinaDireccion direccion,
                   OficinaLatitud latitud, OficinaLongitud longitud, UUID provinciaId) {
        this.id = UUID.randomUUID();
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.provinciaId = provinciaId;
    }

    public Oficina(UUID id, OficinaCodigo codigo, OficinaNombre nombre, OficinaDireccion direccion,
                   OficinaLatitud latitud, OficinaLongitud longitud, UUID provinciaId) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.codigo = Objects.requireNonNull(codigo, "El código es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        this.direccion = Objects.requireNonNull(direccion, "La dirección es obligatoria");
        this.latitud = Objects.requireNonNull(latitud, "La latitud es obligatoria");
        this.longitud = Objects.requireNonNull(longitud, "La longitud es obligatoria");
        this.provinciaId = Objects.requireNonNull(provinciaId, "La provincia es obligatoria");
    }

    public void cambiarNombre(OficinaNombre nuevoNombre) {
        this.nombre = Objects.requireNonNull(nuevoNombre, "El nombre es obligatorio");
    }

    public void cambiarDireccion(OficinaDireccion nuevaDireccion) {
        this.direccion = Objects.requireNonNull(nuevaDireccion, "La dirección es obligatoria");
    }

    public void cambiarUbicacion(OficinaLatitud nuevaLatitud, OficinaLongitud nuevaLongitud) {
        this.latitud = Objects.requireNonNull(nuevaLatitud, "La latitud es obligatoria");
        this.longitud = Objects.requireNonNull(nuevaLongitud, "La longitud es obligatoria");
    }

    public UUID getId() { return id; }
    public OficinaCodigo getCodigo() { return codigo; }
    public OficinaNombre getNombre() { return nombre; }
    public OficinaDireccion getDireccion() { return direccion; }
    public OficinaLatitud getLatitud() { return latitud; }
    public OficinaLongitud getLongitud() { return longitud; }
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