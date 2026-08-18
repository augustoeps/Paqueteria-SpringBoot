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

    @Column(name = "calle", nullable = false)
    private String calle;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    @Column(name = "provincia_id", nullable = false)
    private UUID provinciaId;

    protected OficinaJpaEntity() {
    }

    public OficinaJpaEntity(UUID id, String codigo, String nombre, String calle, String numero,
                            String ciudad, String codigoPostal, UUID provinciaId) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.codigo = Objects.requireNonNull(codigo, "El código es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        this.calle = Objects.requireNonNull(calle, "La calle es obligatoria");
        this.numero = Objects.requireNonNull(numero, "El número es obligatorio");
        this.ciudad = Objects.requireNonNull(ciudad, "La ciudad es obligatoria");
        this.codigoPostal = Objects.requireNonNull(codigoPostal, "El código postal es obligatorio");
        this.provinciaId = Objects.requireNonNull(provinciaId, "La provincia es obligatoria");
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public UUID getProvinciaId() { return provinciaId; }
    public void setProvinciaId(UUID provinciaId) { this.provinciaId = provinciaId; }
}