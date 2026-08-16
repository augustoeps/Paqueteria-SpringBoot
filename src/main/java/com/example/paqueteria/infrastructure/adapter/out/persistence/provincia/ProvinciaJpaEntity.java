package com.example.paqueteria.infrastructure.adapter.out.persistence.provincia;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.util.UUID;

@Entity
@Table(name = "provincias")
public class ProvinciaJpaEntity {

    @Id
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // JPA EXIGE un constructor vacío (sin argumentos)
    protected ProvinciaJpaEntity() {
    }

    // Constructor propio para cuando tú la construyas manualmente en el Mapper
    public ProvinciaJpaEntity(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters (Hibernate los necesita para leer/escribir los valores)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
