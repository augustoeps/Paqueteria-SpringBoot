package com.example.paqueteria.infrastructure.adapter.out.persistence.usuario;

import com.example.paqueteria.application.exception.RecursoNoEncontradoException;
import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioCorreo;
import com.example.paqueteria.domain.valueobjects.UsuarioUsername;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryAdapter(UsuarioMapper usuarioMapper, UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {

        UsuarioJpaEntity usuarioGuardado = this.usuarioMapper.toJpaEntity(usuario);
        UsuarioJpaEntity usuarioFinal = this.usuarioJpaRepository.save(usuarioGuardado);
        return this.usuarioMapper.toDomain(usuarioFinal);

    }
    @Override
    public Optional<Usuario> findById(UUID id) {
        return this.usuarioJpaRepository.findById(id).map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return this.usuarioJpaRepository.findAll().stream().map(usuarioMapper::toDomain).toList();
    }

    @Override
    public boolean delete(UUID id) {
        if(!this.usuarioJpaRepository.existsById(id)){
            return false;
        }
        this.usuarioJpaRepository.deleteById(id);
        return  true;


    }

    @Override
    public Optional<Usuario> findByUsername(UsuarioUsername username) {
        return this.usuarioJpaRepository.findByUsername(username.getValor())
                .map(usuarioMapper::toDomain);
    }
    @Override
    public Usuario update(Usuario usuario) {
        UsuarioJpaEntity entity = this.usuarioMapper.toJpaEntity(usuario);
        UsuarioJpaEntity actualizado = this.usuarioJpaRepository.save(entity);
        return this.usuarioMapper.toDomain(actualizado);
    }

    @Override
    public boolean existsByUsername(UsuarioUsername username) {
        return this.usuarioJpaRepository.existsByUsername(username.getValor());
    }

    @Override
    public boolean existsByEmail(UsuarioCorreo correo) {
        return this.usuarioJpaRepository.existsByCorreo(correo.getValor());
    }
}
