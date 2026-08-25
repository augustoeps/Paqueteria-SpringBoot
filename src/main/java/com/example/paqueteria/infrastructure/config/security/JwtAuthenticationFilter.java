package com.example.paqueteria.infrastructure.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jspecify.annotations.NonNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/*
 * ============================================================================
 * ¿QUÉ FUNCIÓN TIENE ESTA CLASE?
 * ============================================================================
 *
 * JwtAuthenticationFilter es un FILTRO personalizado de Spring Security.
 *
 * Su trabajo es revisar las peticiones HTTP que llegan a nuestra aplicación
 * y comprobar si llevan un JWT en el header:
 *
 *     Authorization: Bearer <token>
 *
 *
 * ¿POR QUÉ NECESITAMOS ESTE FILTRO?
 * ============================================================================
 *
 * Cuando el usuario inicia sesión correctamente, normalmente generamos
 * un JWT y se lo entregamos al cliente.
 *
 * Por ejemplo:
 *
 *     Cliente
 *        │
 *        │ login + contraseña
 *        ▼
 *     Spring Security
 *        │
 *        ▼
 *     autenticación correcta
 *        │
 *        ▼
 *     JwtService
 *        │
 *        ▼
 *     JWT
 *
 *
 * El cliente guarda ese JWT y lo envía posteriormente en cada petición
 * protegida:
 *
 *     Authorization: Bearer eyJhbGciOi...
 *
 *
 * El problema es que HTTP es STATELESS en una autenticación basada en JWT.
 *
 * Es decir, Spring Security no mantiene necesariamente una sesión
 * tradicional con el usuario autenticado.
 *
 * Por eso, en cada nueva petición, tenemos que volver a mirar el JWT
 * y averiguar:
 *
 *     "¿Quién está realizando esta petición?"
 *
 *
 * Ahí entra este filtro.
 *
 *
 * ============================================================================
 * FLUJO GENERAL
 * ============================================================================
 *
 *
 *     PETICIÓN HTTP
 *          │
 *          ▼
 *     JwtAuthenticationFilter
 *          │
 *          ▼
 *     ¿Existe Authorization?
 *          │
 *       ┌──┴──┐
 *       │     │
 *      NO    SÍ
 *       │     │
 *       │     ▼
 *       │   Bearer JWT
 *       │     │
 *       │     ▼
 *       │   extraerUsername()
 *       │     │
 *       │     ▼
 *       │   cargar usuario
 *       │     │
 *       │     ▼
 *       │   validar JWT
 *       │     │
 *       │     ▼
 *       │   Authentication
 *       │     │
 *       │     ▼
 *       │ SecurityContext
 *       │
 *       └───────────────┐
 *                       │
 *                       ▼
 *                filterChain.doFilter()
 *                       │
 *                       ▼
 *                siguiente filtro
 *                       │
 *                       ▼
 *                Controller
 *
 *
 * ============================================================================
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    /*
     * ========================================================================
     * DEPENDENCIA: JwtService
     * ========================================================================
     *
     * Necesitamos JwtService porque este filtro no sabe por sí mismo cómo
     * interpretar un JWT.
     *
     * JwtService es el encargado de trabajar con el token.
     *
     * Por ejemplo:
     *
     *     jwtService.extraerUsername(token)
     *
     *     jwtService.esTokenValido(token, username)
     *
     *
     * Por tanto:
     *
     *     JwtAuthenticationFilter
     *             │
     *             ▼
     *          JwtService
     *             │
     *             ├── extraer username
     *             ├── validar firma
     *             └── comprobar expiración
     */
    private final JwtService jwtService;


    /*
     * ========================================================================
     * DEPENDENCIA: CustomUserDetailsService
     * ========================================================================
     *
     * También necesitamos CustomUserDetailsService.
     *
     * ¿Por qué?
     *
     * Porque el JWT nos puede decir:
     *
     *     username = "juan123"
     *
     * pero el token NO es nuestro objeto Usuario ni nuestro UserDetails.
     *
     * Por eso utilizamos:
     *
     *     userDetailsService.loadUserByUsername(username)
     *
     * para volver a buscar al usuario.
     *
     *
     * El flujo será:
     *
     *     JWT
     *      │
     *      ▼
     *     username
     *      │
     *      ▼
     *     CustomUserDetailsService
     *      │
     *      ▼
     *     Usuario
     *      │
     *      ▼
     *     UsuarioPrincipal
     *      │
     *      ▼
     *     UserDetails
     */
    private final CustomUserDetailsService userDetailsService;


    /*
     * ========================================================================
     * CONSTRUCTOR
     * ========================================================================
     *
     * Spring inyecta automáticamente las dos dependencias que necesita
     * este filtro:
     *
     *     JwtService
     *     CustomUserDetailsService
     *
     * De esta manera el filtro puede:
     *
     *     1. Trabajar con el JWT.
     *     2. Buscar al usuario.
     */
    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    /*
     * ========================================================================
     * doFilterInternal()
     * ========================================================================
     *
     * ESTE ES EL MÉTODO PRINCIPAL DEL FILTRO.
     *
     * OncePerRequestFilter garantiza que este filtro se ejecute una vez
     * por petición.
     *
     *
     * Recibimos tres objetos importantes:
     *
     *
     * HttpServletRequest
     * ------------------
     *
     * Representa la petición HTTP que está llegando.
     *
     * Desde aquí podemos obtener:
     *
     *     - headers
     *     - URL
     *     - método HTTP
     *     - parámetros
     *     - etc.
     *
     *
     * HttpServletResponse
     * -------------------
     *
     * Representa la respuesta que enviaremos al cliente.
     *
     *
     * FilterChain
     * -----------
     *
     * Representa la cadena de filtros.
     *
     * Cuando nuestro filtro termina su trabajo debe permitir que la
     * petición continúe:
     *
     *     filterChain.doFilter(request, response);
     *
     *
     * Esto es MUY importante.
     *
     * Nuestro filtro no debe detener todas las peticiones.
     * Su trabajo es comprobar el JWT y después permitir que la petición
     * continúe hacia los siguientes filtros y posteriormente hacia
     * nuestro Controller.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {


        /*
         * ====================================================================
         * 1. OBTENER EL HEADER Authorization
         * ====================================================================
         *
         * Una petición autenticada mediante JWT normalmente tendrá:
         *
         *     Authorization: Bearer eyJhbGciOi...
         *
         *
         * Utilizamos:
         *
         *     request.getHeader("Authorization")
         *
         * para obtener el contenido de ese header.
         *
         * Por ejemplo:
         *
         *     "Bearer eyJhbGciOi..."
         */
        String authHeader = request.getHeader("Authorization");


        /*
         * ====================================================================
         * 2. COMPROBAR SI EXISTE UN TOKEN
         * ====================================================================
         *
         * Primero comprobamos:
         *
         *     authHeader == null
         *
         * Esto significa que la petición NO tiene header Authorization.
         *
         *
         * También comprobamos:
         *
         *     !authHeader.startsWith("Bearer ")
         *
         * porque esperamos que el header tenga exactamente el formato:
         *
         *     Bearer <token>
         *
         *
         * Ejemplo correcto:
         *
         *     Authorization: Bearer eyJhbGciOi...
         *
         *
         * Si no existe el header o no empieza por "Bearer ", no podemos
         * obtener un JWT.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {


            /*
             * Si no hay JWT, simplemente dejamos continuar la petición.
             *
             * IMPORTANTE:
             *
             * El filtro NO significa:
             *
             *     "Si no hay JWT, rechazo la petición."
             *
             * Significa:
             *
             *     "Si no hay JWT, yo no puedo autenticar esta petición,
             *      así que dejo que los siguientes componentes decidan
             *      qué hacer."
             *
             *
             * Esto permite que existan endpoints públicos como:
             *
             *     /login
             *     /registro
             *
             * que no necesitan un JWT.
             */
            filterChain.doFilter(request, response);

            /*
             * Terminamos este método porque no tiene sentido continuar
             * intentando procesar un token que no existe.
             */
            return;
        }


        /*
         * ====================================================================
         * 3. EXTRAER EL TOKEN
         * ====================================================================
         *
         * Tenemos:
         *
         *     "Bearer eyJhbGciOi..."
         *
         * Pero JwtService necesita únicamente:
         *
         *     "eyJhbGciOi..."
         *
         *
         * "Bearer " tiene 7 caracteres:
         *
         *     B e a r e espacio
         *     1 2 3 4 5   6
         *
         * Por eso:
         *
         *     substring(7)
         *
         * elimina "Bearer " y deja solamente el JWT.
         *
         *
         * Resultado:
         *
         *     authHeader
         *          ↓
         *     "Bearer eyJ..."
         *          ↓
         *     substring(7)
         *          ↓
         *     "eyJ..."
         */
        String token = authHeader.substring(7);


        /*
         * ====================================================================
         * 4. EXTRAER EL USERNAME DEL JWT
         * ====================================================================
         *
         * Ahora utilizamos nuestra clase JwtService.
         *
         * Anteriormente vimos que cuando generábamos el token hacíamos:
         *
         *     .subject(username)
         *
         *
         * Por tanto, el username está almacenado en el claim estándar:
         *
         *     sub
         *
         *
         * JwtService.extraerUsername(token)
         *
         * se encarga de recuperar ese valor.
         *
         *
         * Por ejemplo:
         *
         *     JWT
         *       │
         *       ▼
         *     sub = "juan123"
         *       │
         *       ▼
         *     username = "juan123"
         */
        String username = jwtService.extraerUsername(token);


        /*
         * ====================================================================
         * 5. COMPROBAR DOS COSAS
         * ====================================================================
         *
         * Comprobamos:
         *
         *     username != null
         *
         * y:
         *
         *     SecurityContextHolder.getContext().getAuthentication() == null
         *
         *
         * La primera condición significa:
         *
         *     "Hemos conseguido obtener un username del token."
         *
         *
         * La segunda significa:
         *
         *     "Todavía no hay un usuario autenticado en este contexto."
         *
         *
         * ¿Por qué comprobar que Authentication sea null?
         *
         * Porque no queremos sobrescribir una autenticación que ya exista.
         *
         *
         * Podemos imaginar el SecurityContext como un espacio donde
         * Spring Security guarda:
         *
         *     "¿Quién está autenticado en esta petición?"
         *
         *
         * Si ya existe una Authentication, no necesitamos crear otra.
         */
        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {


            /*
             * =================================================================
             * 6. BUSCAR EL USUARIO MEDIANTE CustomUserDetailsService
             * =================================================================
             *
             * Aquí aparece la clase que acabamos de estudiar.
             *
             * Tenemos:
             *
             *     username
             *
             * obtenido del JWT.
             *
             * Ahora se lo damos a:
             *
             *     CustomUserDetailsService
             *
             *
             * Este servicio:
             *
             *     username
             *        ↓
             *     busca Usuario
             *        ↓
             *     crea UsuarioPrincipal
             *        ↓
             *     devuelve UserDetails
             *
             *
             * Por tanto, aquí volvemos a cargar el usuario.
             */
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);


            /*
             * =================================================================
             * 7. VALIDAR EL JWT
             * =================================================================
             *
             * Ahora comprobamos si el JWT es válido.
             *
             * Utilizamos:
             *
             *     jwtService.esTokenValido(token, username)
             *
             *
             * Recordemos lo que hacía JwtService:
             *
             *     1. Extraía el username del token.
             *     2. Comparaba ese username con el username esperado.
             *     3. Comprobaba que el token no hubiera expirado.
             *
             *
             * Además, al analizar el token mediante JwtService se comprueba
             * la firma utilizando la secretKey.
             *
             *
             * Conceptualmente:
             *
             *     JWT
             *      │
             *      ├── ¿firma correcta?
             *      ├── ¿username correcto?
             *      └── ¿no está expirado?
             *              │
             *              ▼
             *           válido
             */
            if (jwtService.esTokenValido(token, username)) {


                /*
                 * =============================================================
                 * 8. CREAR Authentication
                 * =============================================================
                 *
                 * Este es uno de los puntos MÁS IMPORTANTES de todo
                 * Spring Security.
                 *
                 * Hasta este momento tenemos:
                 *
                 *     JWT válido
                 *     +
                 *     Usuario encontrado
                 *
                 * Pero Spring Security todavía necesita saber:
                 *
                 *     "Esta petición está autenticada como este usuario."
                 *
                 *
                 * Para eso creamos:
                 *
                 *     UsernamePasswordAuthenticationToken
                 *
                 *
                 * Aunque su nombre contiene "Password", aquí NO estamos
                 * realizando un login ni comprobando una contraseña.
                 *
                 * En este caso lo utilizamos como objeto Authentication
                 * que representa al usuario ya autenticado.
                 */
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );


                /*
                 * =============================================================
                 * ¿QUÉ ESTAMOS PASANDO AL AuthenticationToken?
                 * =============================================================
                 *
                 * Constructor:
                 *
                 *     new UsernamePasswordAuthenticationToken(
                 *          userDetails,
                 *          null,
                 *          userDetails.getAuthorities()
                 *     );
                 *
                 *
                 * PRIMER ARGUMENTO:
                 *
                 *     userDetails
                 *
                 * Es el usuario autenticado.
                 *
                 * En nuestro caso será realmente:
                 *
                 *     UsuarioPrincipal
                 *
                 *
                 * SEGUNDO ARGUMENTO:
                 *
                 *     null
                 *
                 * Aquí normalmente estaría la contraseña, pero en este
                 * punto NO necesitamos una contraseña.
                 *
                 * El JWT ya ha sido validado.
                 *
                 *
                 * TERCER ARGUMENTO:
                 *
                 *     userDetails.getAuthorities()
                 *
                 * Son los roles/autorizaciones del usuario.
                 *
                 * Por ejemplo:
                 *
                 *     ROLE_ADMIN
                 *
                 *
                 * Por tanto, estamos diciendo:
                 *
                 *     "Este UserDetails es el usuario autenticado
                 *      y estas son sus autoridades."
                 */


                /*
                 * =============================================================
                 * 9. AÑADIR DETALLES DE LA PETICIÓN
                 * =============================================================
                 *
                 * Este método añade información adicional relacionada con
                 * la petición HTTP.
                 *
                 * WebAuthenticationDetailsSource puede incluir información
                 * como la dirección IP del cliente y el ID de sesión HTTP
                 * cuando corresponda.
                 *
                 * No es lo que identifica al usuario.
                 *
                 * El usuario ya está representado por userDetails.
                 *
                 * Esto simplemente añade detalles de contexto de la petición.
                 */
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );


                /*
                 * =============================================================
                 * 10. GUARDAR LA AUTENTICACIÓN EN SecurityContext
                 * =============================================================
                 *
                 * ESTA ES OTRA DE LAS PARTES MÁS IMPORTANTES.
                 *
                 * Ahora tenemos un Authentication válido:
                 *
                 *     authToken
                 *
                 * Pero necesitamos entregárselo a Spring Security.
                 *
                 * Para ello utilizamos:
                 *
                 *     SecurityContextHolder
                 *
                 *
                 * El SecurityContext representa el contexto de seguridad
                 * de la petición actual.
                 *
                 * Al hacer:
                 *
                 *     setAuthentication(authToken)
                 *
                 * estamos diciendo:
                 *
                 *     "Para esta petición, el usuario autenticado es este."
                 *
                 *
                 * El flujo queda:
                 *
                 *     JWT válido
                 *          │
                 *          ▼
                 *     UsuarioPrincipal
                 *          │
                 *          ▼
                 *     Authentication
                 *          │
                 *          ▼
                 *     SecurityContext
                 *
                 *
                 * A partir de aquí Spring Security puede saber quién
                 * está autenticado.
                 */
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }


        /*
         * ====================================================================
         * 11. CONTINUAR LA CADENA DE FILTROS
         * ====================================================================
         *
         * Después de hacer nuestro trabajo debemos permitir que la petición
         * continúe.
         *
         * La petición seguirá pasando por los siguientes filtros de
         * Spring Security y finalmente podrá llegar al Controller si
         * tiene autorización.
         *
         *
         * Podemos imaginar FilterChain como:
         *
         *
         *     Petición
         *        │
         *        ▼
         *     Filtro 1
         *        │
         *        ▼
         *     Filtro 2
         *        │
         *        ▼
         *     JwtAuthenticationFilter
         *        │
         *        ▼
         *     Filtro siguiente
         *        │
         *        ▼
         *     Spring Security
         *        │
         *        ▼
         *     Controller
         *
         *
         * Si nuestro filtro no ejecutara doFilter(), la petición podría
         * quedarse detenida en este punto.
         */
        filterChain.doFilter(request, response);
    }
}
