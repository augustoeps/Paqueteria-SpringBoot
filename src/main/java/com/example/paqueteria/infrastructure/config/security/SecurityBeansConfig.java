package com.example.paqueteria.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/*
 * ============================================================================
 * ¿QUÉ FUNCIÓN TIENE ESTA CLASE?
 * ============================================================================
 *
 * SecurityBeansConfig es la clase que CONFIGURA Spring Security.
 *
 * Las clases anteriores tenían responsabilidades concretas:
 *
 *     UsuarioPrincipal
 *         → adapta Usuario a UserDetails.
 *
 *     CustomUserDetailsService
 *         → busca el Usuario y crea UsuarioPrincipal.
 *
 *     JwtService
 *         → genera y valida JWT.
 *
 *     JwtAuthenticationFilter
 *         → lee el JWT de cada petición y crea la autenticación.
 *
 * Esta clase es la que conecta todas esas piezas y le dice a Spring Security
 * cómo debe utilizarlas.
 *
 * Es decir:
 *
 *     SecurityBeansConfig
 *            │
 *            ├── PasswordEncoder
 *            ├── AuthenticationProvider
 *            ├── AuthenticationManager
 *            └── SecurityFilterChain
 *
 */
@Configuration
@EnableMethodSecurity
public class SecurityBeansConfig {

    /*
     * CustomUserDetailsService será utilizado por el
     * DaoAuthenticationProvider para buscar usuarios.
     */
    private final CustomUserDetailsService userDetailsService;

    /*
     * Nuestro filtro personalizado será añadido a la cadena de filtros
     * de Spring Security para procesar los JWT.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    /*
     * Constructor mediante el cual Spring inyecta las dos dependencias
     * que necesita esta configuración.
     */
    public SecurityBeansConfig(
            CustomUserDetailsService userDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    /*
     * ========================================================================
     * PasswordEncoder
     * ========================================================================
     *
     * Define cómo se cifran/hashean las contraseñas.
     *
     * BCrypt NO guarda la contraseña original.
     *
     * Por ejemplo:
     *
     *     "123456"
     *          ↓
     *     BCrypt
     *          ↓
     *     "$2a$10$..."
     *
     * Spring Security utilizará este PasswordEncoder posteriormente para
     * comparar la contraseña introducida por el usuario con el hash
     * almacenado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*
     * ========================================================================
     * DaoAuthenticationProvider
     * ========================================================================
     *
     * Este componente es MUY importante para el LOGIN.
     *
     * Su función es coordinar:
     *
     *     1. Buscar el usuario.
     *     2. Obtener su contraseña almacenada.
     *     3. Utilizar PasswordEncoder para comprobar la contraseña.
     *
     *
     * Le damos nuestro:
     *
     *     CustomUserDetailsService
     *
     * para que sepa cómo encontrar al usuario.
     *
     * Y después le damos:
     *
     *     PasswordEncoder
     *
     * para que pueda comprobar la contraseña.
     *
     *
     * Por tanto:
     *
     *     Authentication
     *            ↓
     *     DaoAuthenticationProvider
     *            │
     *            ├── CustomUserDetailsService
     *            │       ↓
     *            │     Usuario
     *            │
     *            └── PasswordEncoder
     *                    ↓
     *              comprueba password
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    /*
     * ========================================================================
     * AuthenticationManager
     * ========================================================================
     *
     * AuthenticationManager es el componente que Spring Security utiliza
     * para realizar el proceso de autenticación.
     *
     * No es quien necesariamente comprueba directamente la contraseña.
     *
     * Se encarga de delegar la autenticación al AuthenticationProvider
     * correspondiente.
     *
     * En nuestro caso:
     *
     *     AuthenticationManager
     *            ↓
     *     DaoAuthenticationProvider
     *            ↓
     *     CustomUserDetailsService
     *            +
     *     PasswordEncoder
     *
     *
     * Por eso es una pieza fundamental del LOGIN.
     */



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /*
     * ========================================================================
     * SecurityFilterChain
     * ========================================================================
     *
     * Aquí configuramos LAS REGLAS DE SEGURIDAD HTTP.
     *
     * También aquí incorporamos nuestro JwtAuthenticationFilter.
     *
     * Es decir, aquí le decimos a Spring Security:
     *
     *     - qué endpoints son públicos.
     *     - qué endpoints necesitan autenticación.
     *     - qué filtros utilizar.
     *     - qué AuthenticationProvider utilizar.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                /*
                 * ============================================================
                 * CSRF
                 * ============================================================
                 *
                 * Desactivamos CSRF.
                 *
                 * En una API REST que utiliza JWT y no depende de una sesión
                 * basada en cookies, normalmente se configura de esta manera.
                 */
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                /*
                 * ============================================================
                 * AUTORIZACIÓN DE PETICIONES
                 * ============================================================
                 *
                 * Definimos qué rutas pueden utilizarse sin estar autenticado.
                 *
                 * "/auth/**"
                 *
                 * significa que cualquier endpoint que empiece por:
                 *
                 *     /auth/
                 *
                 * será público.
                 *
                 * Por ejemplo:
                 *
                 *     POST /auth/login
                 *
                 * puede ser utilizado sin JWT porque precisamente necesitamos
                 * poder iniciar sesión para obtener el JWT.
                 *
                 *
                 * Después:
                 *
                 *     .anyRequest().authenticated()
                 *
                 * significa:
                 *
                 *     "Cualquier otra petición necesita estar autenticada."
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/provincias/**").permitAll()
                        .requestMatchers("/tarifas/cotizar").permitAll()
                        .requestMatchers("/paquetes/codigo/**").permitAll()
                        .requestMatchers("/paquetes").permitAll()
                        .requestMatchers("/historial-estados/paquete/**").permitAll()
                        .requestMatchers("/oficinas").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )


                /*
                 * ============================================================
                 * AUTHENTICATION PROVIDER
                 * ============================================================
                 *
                 * Indicamos a Spring Security qué AuthenticationProvider
                 * debe utilizar para autenticar usuarios.
                 *
                 * Nuestro provider utiliza:
                 *
                 *     CustomUserDetailsService
                 *     +
                 *     BCryptPasswordEncoder
                 */
                .authenticationProvider(authenticationProvider())


                /*
                 * ============================================================
                 * JWT FILTER
                 * ============================================================
                 *
                 * Añadimos nuestro filtro personalizado a la cadena de
                 * filtros de Spring Security.
                 *
                 * Lo colocamos antes de:
                 *
                 *     UsernamePasswordAuthenticationFilter
                 *
                 * De esta manera nuestro filtro puede revisar primero si
                 * la petición contiene un JWT.
                 *
                 *
                 * El flujo será aproximadamente:
                 *
                 *     HTTP Request
                 *          ↓
                 *     JwtAuthenticationFilter
                 *          ↓
                 *     SecurityContext
                 *          ↓
                 *     resto de Spring Security
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        /*
         * Construimos finalmente la cadena de filtros configurada.
         */
        return http.build();
    }
}
