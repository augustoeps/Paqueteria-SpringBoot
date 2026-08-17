package com.example.paqueteria.infrastructure.adapter.out.persistence.oficina;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.util.Objects;
import java.util.UUID;
@Entity
@Table(name = "oficinas")
public class OficinaJpaEntity {

    @Id
    private UUID id;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "provincia_id", nullable = false)
    private UUID provinciaId;

    protected OficinaJpaEntity() {
    }

    public OficinaJpaEntity(UUID id, String codigo, String nombre, String direccion, UUID provinciaId) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.codigo = Objects.requireNonNull(codigo, "El código es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        this.direccion = Objects.requireNonNull(direccion, "La dirección es obligatoria");
        this.provinciaId = Objects.requireNonNull(provinciaId, "La provincia es obligatoria");
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public UUID getProvinciaId() { return provinciaId; }
    public void setProvinciaId(UUID provinciaId) { this.provinciaId = provinciaId; }
}
