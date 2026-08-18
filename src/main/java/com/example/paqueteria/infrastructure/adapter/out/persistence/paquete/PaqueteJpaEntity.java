package com.example.paqueteria.infrastructure.adapter.out.persistence.paquete;

import com.example.paqueteria.domain.enums.EstadoPaquete;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "paquetes")
public class PaqueteJpaEntity {

    @Id
    private UUID id;

    @Column(name = "codigo_seguimiento", nullable = false, unique = true)
    private String codigoSeguimiento;

    @Column(name = "peso", nullable = false)
    private BigDecimal peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_paquete", nullable = false)
    private EstadoPaquete estadoPaquete;

    @Column(name = "oficina_origen_id", nullable = false)
    private UUID oficinaOrigenId;

    @Column(name = "oficina_destino_id", nullable = false)
    private UUID oficinaDestinoId;

    // --- Remitente ---
    @Column(name = "remitente_nombre", nullable = false)
    private String remitenteNombre;

    @Column(name = "remitente_telefono", nullable = false)
    private String remitenteTelefono;

    @Column(name = "remitente_email", nullable = false)
    private String remitenteEmail;

    @Column(name = "remitente_calle", nullable = false)
    private String remitenteCalle;

    @Column(name = "remitente_ciudad", nullable = false)
    private String remitenteCiudad;

    @Column(name = "remitente_provincia", nullable = false)
    private String remitenteProvincia;

    @Column(name = "remitente_codigo_postal", nullable = false)
    private String remitenteCodigoPostal;

    // --- Destinatario ---
    @Column(name = "destinatario_nombre", nullable = false)
    private String destinatarioNombre;

    @Column(name = "destinatario_telefono", nullable = false)
    private String destinatarioTelefono;

    @Column(name = "destinatario_email", nullable = false)
    private String destinatarioEmail;

    @Column(name = "destinatario_calle", nullable = false)
    private String destinatarioCalle;

    @Column(name = "destinatario_ciudad", nullable = false)
    private String destinatarioCiudad;

    @Column(name = "destinatario_provincia", nullable = false)
    private String destinatarioProvincia;

    @Column(name = "destinatario_codigo_postal", nullable = false)
    private String destinatarioCodigoPostal;

    @Column(name = "tarifa_aplicada", nullable = false)
    private BigDecimal tarifaAplicada;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    protected PaqueteJpaEntity() {
    }

    public PaqueteJpaEntity(UUID id, String codigoSeguimiento, BigDecimal peso, EstadoPaquete estadoPaquete,
                            UUID oficinaOrigenId, UUID oficinaDestinoId,
                            String remitenteNombre, String remitenteTelefono, String remitenteEmail,
                            String remitenteCalle, String remitenteCiudad, String remitenteProvincia, String remitenteCodigoPostal,
                            String destinatarioNombre, String destinatarioTelefono, String destinatarioEmail,
                            String destinatarioCalle, String destinatarioCiudad, String destinatarioProvincia, String destinatarioCodigoPostal,
                            BigDecimal tarifaAplicada, LocalDateTime fechaCreacion) {
        this.id = id;
        this.codigoSeguimiento = codigoSeguimiento;
        this.peso = peso;
        this.estadoPaquete = estadoPaquete;
        this.oficinaOrigenId = oficinaOrigenId;
        this.oficinaDestinoId = oficinaDestinoId;
        this.remitenteNombre = remitenteNombre;
        this.remitenteTelefono = remitenteTelefono;
        this.remitenteEmail = remitenteEmail;
        this.remitenteCalle = remitenteCalle;
        this.remitenteCiudad = remitenteCiudad;
        this.remitenteProvincia = remitenteProvincia;
        this.remitenteCodigoPostal = remitenteCodigoPostal;
        this.destinatarioNombre = destinatarioNombre;
        this.destinatarioTelefono = destinatarioTelefono;
        this.destinatarioEmail = destinatarioEmail;
        this.destinatarioCalle = destinatarioCalle;
        this.destinatarioCiudad = destinatarioCiudad;
        this.destinatarioProvincia = destinatarioProvincia;
        this.destinatarioCodigoPostal = destinatarioCodigoPostal;
        this.tarifaAplicada = tarifaAplicada;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters de TODOS los campos
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigoSeguimiento() { return codigoSeguimiento; }
    public void setCodigoSeguimiento(String codigoSeguimiento) { this.codigoSeguimiento = codigoSeguimiento; }

    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }

    public EstadoPaquete getEstadoPaquete() { return estadoPaquete; }
    public void setEstadoPaquete(EstadoPaquete estadoPaquete) { this.estadoPaquete = estadoPaquete; }

    public UUID getOficinaOrigenId() { return oficinaOrigenId; }
    public void setOficinaOrigenId(UUID oficinaOrigenId) { this.oficinaOrigenId = oficinaOrigenId; }

    public UUID getOficinaDestinoId() { return oficinaDestinoId; }
    public void setOficinaDestinoId(UUID oficinaDestinoId) { this.oficinaDestinoId = oficinaDestinoId; }

    public String getRemitenteNombre() { return remitenteNombre; }
    public void setRemitenteNombre(String remitenteNombre) { this.remitenteNombre = remitenteNombre; }

    public String getRemitenteTelefono() { return remitenteTelefono; }
    public void setRemitenteTelefono(String remitenteTelefono) { this.remitenteTelefono = remitenteTelefono; }

    public String getRemitenteEmail() { return remitenteEmail; }
    public void setRemitenteEmail(String remitenteEmail) { this.remitenteEmail = remitenteEmail; }

    public String getRemitenteCalle() { return remitenteCalle; }
    public void setRemitenteCalle(String remitenteCalle) { this.remitenteCalle = remitenteCalle; }

    public String getRemitenteCiudad() { return remitenteCiudad; }
    public void setRemitenteCiudad(String remitenteCiudad) { this.remitenteCiudad = remitenteCiudad; }

    public String getRemitenteProvincia() { return remitenteProvincia; }
    public void setRemitenteProvincia(String remitenteProvincia) { this.remitenteProvincia = remitenteProvincia; }

    public String getRemitenteCodigoPostal() { return remitenteCodigoPostal; }
    public void setRemitenteCodigoPostal(String remitenteCodigoPostal) { this.remitenteCodigoPostal = remitenteCodigoPostal; }

    public String getDestinatarioNombre() { return destinatarioNombre; }
    public void setDestinatarioNombre(String destinatarioNombre) { this.destinatarioNombre = destinatarioNombre; }

    public String getDestinatarioTelefono() { return destinatarioTelefono; }
    public void setDestinatarioTelefono(String destinatarioTelefono) { this.destinatarioTelefono = destinatarioTelefono; }

    public String getDestinatarioEmail() { return destinatarioEmail; }
    public void setDestinatarioEmail(String destinatarioEmail) { this.destinatarioEmail = destinatarioEmail; }

    public String getDestinatarioCalle() { return destinatarioCalle; }
    public void setDestinatarioCalle(String destinatarioCalle) { this.destinatarioCalle = destinatarioCalle; }

    public String getDestinatarioCiudad() { return destinatarioCiudad; }
    public void setDestinatarioCiudad(String destinatarioCiudad) { this.destinatarioCiudad = destinatarioCiudad; }

    public String getDestinatarioProvincia() { return destinatarioProvincia; }
    public void setDestinatarioProvincia(String destinatarioProvincia) { this.destinatarioProvincia = destinatarioProvincia; }

    public String getDestinatarioCodigoPostal() { return destinatarioCodigoPostal; }
    public void setDestinatarioCodigoPostal(String destinatarioCodigoPostal) { this.destinatarioCodigoPostal = destinatarioCodigoPostal; }

    public BigDecimal getTarifaAplicada() { return tarifaAplicada; }
    public void setTarifaAplicada(BigDecimal tarifaAplicada) { this.tarifaAplicada = tarifaAplicada; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}