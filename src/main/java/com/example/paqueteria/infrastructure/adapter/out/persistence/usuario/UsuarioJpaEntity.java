package com.example.paqueteria.infrastructure.adapter.out.persistence.usuario;

import com.example.paqueteria.domain.enums.UsuarioRol;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    @Id
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "contrasena_hash", nullable = false)
    private String contrasenaHash;

    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private UsuarioRol rol;

    protected UsuarioJpaEntity() {
    }

    public UsuarioJpaEntity(UUID id, String nombre, String apellido, String username,
                            String contrasenaHash, String correo, UsuarioRol rol) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        this.apellido = Objects.requireNonNull(apellido, "El apellido es obligatorio");
        this.username = Objects.requireNonNull(username, "El username es obligatorio");
        this.contrasenaHash = Objects.requireNonNull(contrasenaHash, "La contraseña es obligatoria");
        this.correo = Objects.requireNonNull(correo, "El correo es obligatorio");
        this.rol = Objects.requireNonNull(rol, "El rol es obligatorio");
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getContrasenaHash() { return contrasenaHash; }
    public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public UsuarioRol getRol() { return rol; }
    public void setRol(UsuarioRol rol) { this.rol = rol; }
}