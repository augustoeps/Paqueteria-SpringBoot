package com.example.paqueteria.infrastructure.adapter.in.web.usuario;


import com.example.paqueteria.application.port.in.usuario.*;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.enums.UsuarioRol;
import com.example.paqueteria.domain.valueobjects.*;
import com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto.CambiarContrasenaRequest;
import com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto.CambiarRolRequest;
import com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto.CrearUsuarioRequest;
import com.example.paqueteria.infrastructure.adapter.in.web.usuario.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/Usuarios")
public class UsuarioController  {


    private final CreateUsuarioUseCase createUsuarioUseCase;
    private final FindByIdUsuarioUseCase findByIdUsuarioUseCase;
    private final FindAllUsuarioUseCase findAllUsuarioUseCase;
    private final FindByUsernameUsuarioUseCase findByUsernameUsuarioUseCase;
    private final DeleteUsuarioUseCase deleteUsuarioUseCase;
    private final ChangePasswordUsuarioUseCase changePasswordUsuarioUseCase;
    private final ChangeRolUsuarioUseCase changeRolUsuarioUseCase;

    public UsuarioController(CreateUsuarioUseCase createUsuarioUseCase, FindByIdUsuarioUseCase findByIdUsuarioUseCase, FindAllUsuarioUseCase findAllUsuarioUseCase, FindByUsernameUsuarioUseCase findByUsernameUsuarioUseCase, DeleteUsuarioUseCase deleteUsuarioUseCase, ChangePasswordUsuarioUseCase changePasswordUsuarioUseCase, ChangeRolUsuarioUseCase changeRolUsuarioUseCase) {
        this.createUsuarioUseCase = createUsuarioUseCase;
        this.findByIdUsuarioUseCase = findByIdUsuarioUseCase;
        this.findAllUsuarioUseCase = findAllUsuarioUseCase;
        this.findByUsernameUsuarioUseCase = findByUsernameUsuarioUseCase;
        this.deleteUsuarioUseCase = deleteUsuarioUseCase;
        this.changePasswordUsuarioUseCase = changePasswordUsuarioUseCase;
        this.changeRolUsuarioUseCase = changeRolUsuarioUseCase;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody CrearUsuarioRequest request){

        UsuarioNombre usuarioNombre =  new UsuarioNombre(request.nombre());
        UsuarioApellido usuarioApellido =  new UsuarioApellido(request.apellido());
        UsuarioUsername usuarioUsername = new UsuarioUsername(request.username());
        UsuarioContrasena usuarioContrasena = new UsuarioContrasena(request.contrasena());
        UsuarioCorreo usuarioCorreo = new UsuarioCorreo(request.correo());

        Usuario usuario = this.createUsuarioUseCase.create(usuarioNombre,usuarioApellido,usuarioUsername,usuarioContrasena,usuarioCorreo,UsuarioRol.CLIENTE);

        return ResponseEntity.ok(UsuarioResponse.desde(usuario));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable UUID id) {

        Optional<Usuario> usuario = this.findByIdUsuarioUseCase.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(UsuarioResponse.desde(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {

        List<Usuario> usuarios = this.findAllUsuarioUseCase.findAll();

        List<UsuarioResponse> response = usuarios.stream()
                .map(UsuarioResponse::desde)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        boolean eliminado = this.deleteUsuarioUseCase.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioResponse> findByUserName(@PathVariable String username) {
        UsuarioUsername usuarioUsername = new UsuarioUsername(username);
        Optional<Usuario> usuario = this.findByUsernameUsuarioUseCase.findByUsername(usuarioUsername);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(UsuarioResponse.desde(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/contrasena")
    public ResponseEntity<UsuarioResponse> cambiarContrasena(@PathVariable UUID id, @Valid @RequestBody CambiarContrasenaRequest request) {

        UsuarioContrasena contrasena = new UsuarioContrasena(request.contrasena());

        Usuario usuario = this.changePasswordUsuarioUseCase.changePassword(id,contrasena);

        return ResponseEntity.ok(UsuarioResponse.desde(usuario));

    }

    @PatchMapping("/{id}/rol")
    public ResponseEntity<UsuarioResponse> cambiarContrasena(@PathVariable UUID id, @Valid @RequestBody CambiarRolRequest request) {

        UsuarioRol nuevoRol = UsuarioRol.valueOf(request.rol());

        Usuario usuario = this.changeRolUsuarioUseCase.changeRol(id,nuevoRol);

        return ResponseEntity.ok(UsuarioResponse.desde(usuario));

    }






    }
