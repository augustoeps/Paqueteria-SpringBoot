package com.example.paqueteria.infrastructure.config.security;

import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioUsername;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public CustomUserDetailsService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioUsername usuarioUsername =  new UsuarioUsername(username);
        Usuario usuario = usuarioRepositoryPort.findByUsername(usuarioUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new UsuarioPrincipal(usuario);
    }
}