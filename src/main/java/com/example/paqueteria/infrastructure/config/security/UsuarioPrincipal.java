package com.example.paqueteria.infrastructure.config.security;

import com.example.paqueteria.domain.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {

    private final Usuario usuario;

    public UsuarioPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolConPrefijo = "ROLE_" + usuario.getUsuarioRol().name();
        return List.of(new SimpleGrantedAuthority(rolConPrefijo));
    }

    @Override
    public String getPassword() {
        return usuario.getUsuarioContrasenaHash().getHash();
    }

    @Override
    public String getUsername() {
        return usuario.getUsuarioUsername().getValor();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}