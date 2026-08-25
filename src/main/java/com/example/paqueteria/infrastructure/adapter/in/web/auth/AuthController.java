package com.example.paqueteria.infrastructure.adapter.in.web.auth;


import com.example.paqueteria.infrastructure.adapter.in.web.auth.dto.LoginRequest;
import com.example.paqueteria.infrastructure.adapter.in.web.auth.dto.LoginResponse;
import com.example.paqueteria.infrastructure.config.security.CustomUserDetailsService;
import com.example.paqueteria.infrastructure.config.security.JwtService;
import com.example.paqueteria.infrastructure.config.security.UsuarioPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        UsuarioPrincipal principal = (UsuarioPrincipal)  userDetails;
        String rol = principal.getUsuario().getUsuarioRol().name();

        String token =  jwtService.generarToken(request.username(), rol);

        return ResponseEntity.ok(new LoginResponse(token));

    }
}
