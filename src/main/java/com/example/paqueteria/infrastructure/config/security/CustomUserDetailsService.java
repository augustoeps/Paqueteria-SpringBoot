package com.example.paqueteria.infrastructure.config.security;

import com.example.paqueteria.application.port.out.usuario.UsuarioRepositoryPort;
import com.example.paqueteria.domain.entity.Usuario;
import com.example.paqueteria.domain.valueobjects.UsuarioUsername;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/*
 * ============================================================================
 * ¿QUÉ FUNCIÓN TIENE ESTA CLASE?
 * ============================================================================
 *
 * CustomUserDetailsService es el componente encargado de decirle a
 * Spring Security CÓMO ENCONTRAR UN USUARIO.
 *
 * Spring Security necesita poder obtener los datos de un usuario cuando
 * alguien intenta autenticarse.
 *
 * Por ejemplo, imaginemos que el usuario intenta iniciar sesión con:
 *
 *     username = "juan123"
 *     password = "123456"
 *
 * Spring Security necesita encontrar en nuestra aplicación al usuario
 * llamado "juan123" para poder obtener:
 *
 *     - su username
 *     - su contraseña almacenada (hash)
 *     - sus roles
 *     - el estado de su cuenta
 *
 * El problema es que Spring Security NO sabe cómo acceder a nuestra
 * base de datos ni sabe cómo está diseñada nuestra entidad Usuario.
 *
 * Por eso nosotros implementamos UserDetailsService.
 *
 *
 * La interfaz:
 *
 *     UserDetailsService
 *
 * le dice a Spring Security:
 *
 *     "Cuando necesites buscar un usuario, utiliza este servicio."
 *
 *
 * Nuestra implementación:
 *
 *     CustomUserDetailsService
 *
 * se encarga de:
 *
 *     1. Recibir un username.
 *     2. Buscar ese usuario mediante nuestro repositorio.
 *     3. Si existe, obtener nuestro Usuario.
 *     4. Convertir nuestro Usuario en UsuarioPrincipal.
 *     5. Devolverlo como UserDetails.
 *
 *
 * El flujo sería:
 *
 *
 *     Spring Security
 *           │
 *           │ "Necesito al usuario juan123"
 *           ▼
 *     CustomUserDetailsService
 *           │
 *           ▼
 *     UsuarioRepositoryPort
 *           │
 *           ▼
 *     Base de datos
 *           │
 *           ▼
 *     Usuario
 *           │
 *           ▼
 *     UsuarioPrincipal
 *           │
 *           ▼
 *     UserDetails
 *           │
 *           ▼
 *     Spring Security
 *
 *
 * ============================================================================
 *
 * ¿POR QUÉ NO BUSCAMOS DIRECTAMENTE EL USUARIO EN ESTA CLASE?
 *
 * ============================================================================
 *
 * Porque estamos utilizando una arquitectura donde el acceso a los datos
 * está separado mediante un puerto:
 *
 *     UsuarioRepositoryPort
 *
 * CustomUserDetailsService no necesita saber si el usuario viene de:
 *
 *     - MySQL
 *     - PostgreSQL
 *     - MongoDB
 *     - otra fuente
 *
 * Simplemente le dice al puerto:
 *
 *     "búscame este usuario"
 *
 * Esta separación es especialmente importante porque esta clase pertenece
 * a la infraestructura de seguridad, mientras que Usuario pertenece al
 * dominio.
 *
 *
 * ============================================================================
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /*
     * ========================================================================
     * ATRIBUTO usuarioRepositoryPort
     * ========================================================================
     *
     * Este objeto es el encargado de buscar usuarios.
     *
     * No utilizamos directamente un repositorio de JPA aquí.
     *
     * En su lugar utilizamos:
     *
     *     UsuarioRepositoryPort
     *
     * que es un puerto de salida de nuestra aplicación.
     *
     * Esto permite mantener desacoplada la lógica de seguridad de la
     * implementación concreta de la base de datos.
     *
     * Podemos imaginar:
     *
     *     CustomUserDetailsService
     *              │
     *              ▼
     *     UsuarioRepositoryPort
     *              │
     *              ▼
     *     Implementación del repositorio
     *              │
     *              ▼
     *          Base de datos
     *
     *
     * Por tanto, CustomUserDetailsService no necesita saber cómo se realiza
     * realmente la consulta.
     */
    private final UsuarioRepositoryPort usuarioRepositoryPort;


    /*
     * ========================================================================
     * CONSTRUCTOR
     * ========================================================================
     *
     * Spring utiliza inyección de dependencias para proporcionar
     * UsuarioRepositoryPort.
     *
     * Cuando Spring crea CustomUserDetailsService, le entrega una
     * implementación de UsuarioRepositoryPort.
     *
     * Así podemos utilizar:
     *
     *     usuarioRepositoryPort.findByUsername(...)
     *
     * sin tener que crear nosotros manualmente el repositorio.
     *
     * Esto también facilita las pruebas porque podemos sustituir el
     * repositorio real por un mock.
     */
    public CustomUserDetailsService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }


    /*
     * ========================================================================
     * loadUserByUsername()
     * ========================================================================
     *
     * ESTE ES EL MÉTODO MÁS IMPORTANTE DE ESTA CLASE.
     *
     * Es un método definido por la interfaz UserDetailsService.
     *
     * Spring Security lo utilizará cuando necesite cargar un usuario
     * mediante su username.
     *
     *
     * Por ejemplo:
     *
     *     username = "juan123"
     *
     * Spring Security terminará solicitando:
     *
     *     loadUserByUsername("juan123")
     *
     *
     * Nuestra responsabilidad es:
     *
     *     1. Buscar "juan123".
     *     2. Encontrar nuestro Usuario.
     *     3. Convertirlo a UsuarioPrincipal.
     *     4. Devolverlo como UserDetails.
     *
     *
     * IMPORTANTE:
     *
     * Este método NO comprueba directamente la contraseña.
     *
     * Su trabajo es ENCONTRAR Y DEVOLVER EL USUARIO.
     *
     * La comprobación de la contraseña se realizará posteriormente
     * mediante el mecanismo de autenticación de Spring Security y
     * normalmente utilizando un PasswordEncoder.
     */
    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException {


        /*
         * ====================================================================
         * 1. CONVERTIMOS EL STRING EN NUESTRO VALUE OBJECT
         * ====================================================================
         *
         * Spring Security nos proporciona:
         *
         *     String username
         *
         * Pero nuestro dominio utiliza:
         *
         *     UsuarioUsername
         *
         * Por eso creamos:
         *
         *     new UsuarioUsername(username)
         *
         *
         * De esta manera estamos respetando el modelo de nuestro dominio.
         *
         * Por ejemplo:
         *
         *     "juan123"
         *          │
         *          ▼
         *     UsuarioUsername("juan123")
         *
         *
         * En lugar de utilizar directamente un String en toda nuestra
         * aplicación, nuestro dominio encapsula el username dentro de
         * un Value Object.
         */
        UsuarioUsername usuarioUsername = new UsuarioUsername(username);


        /*
         * ====================================================================
         * 2. BUSCAMOS EL USUARIO
         * ====================================================================
         *
         * Ahora utilizamos nuestro puerto de repositorio:
         *
         *     usuarioRepositoryPort
         *
         * para buscar el usuario mediante el username.
         *
         * El método:
         *
         *     findByUsername(usuarioUsername)
         *
         * devuelve un Optional<Usuario>.
         *
         * Esto significa que el usuario puede:
         *
         *     - existir
         *     - no existir
         *
         *
         * Conceptualmente:
         *
         *     CustomUserDetailsService
         *              │
         *              ▼
         *     UsuarioRepositoryPort
         *              │
         *              ▼
         *     findByUsername(...)
         *              │
         *        ┌─────┴─────┐
         *        │           │
         *     existe       no existe
         *        │           │
         *        ▼           ▼
         *     Usuario     Optional vacío
         */
        Usuario usuario = usuarioRepositoryPort.findByUsername(usuarioUsername)


                /*
                 * ============================================================
                 * 3. ¿QUÉ PASA SI EL USUARIO NO EXISTE?
                 * ============================================================
                 *
                 * Si el Optional está vacío, ejecutamos:
                 *
                 *     orElseThrow(...)
                 *
                 * y lanzamos:
                 *
                 *     UsernameNotFoundException
                 *
                 *
                 * Esta excepción es precisamente la excepción que
                 * UserDetailsService utiliza cuando no encuentra al usuario.
                 *
                 * Es importante porque Spring Security puede interpretar
                 * correctamente que el usuario solicitado no existe.
                 */
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado: " + username
                        )
                );


        /*
         * ====================================================================
         * 4. CONVERTIMOS Usuario EN UsuarioPrincipal
         * ====================================================================
         *
         * Si hemos llegado hasta aquí significa que el usuario SÍ existe.
         *
         * Ahora tenemos:
         *
         *     Usuario usuario
         *
         * Pero Spring Security necesita:
         *
         *     UserDetails
         *
         *
         * Aquí aparece la clase que estudiamos anteriormente:
         *
         *     UsuarioPrincipal
         *
         *
         * UsuarioPrincipal implementa:
         *
         *     UserDetails
         *
         *
         * Por tanto hacemos:
         *
         *     new UsuarioPrincipal(usuario)
         *
         *
         * Y obtenemos:
         *
         *     UsuarioPrincipal
         *
         * que Spring Security puede utilizar como UserDetails.
         *
         *
         * El flujo completo es:
         *
         *     username
         *        │
         *        ▼
         *     UsuarioUsername
         *        │
         *        ▼
         *     UsuarioRepositoryPort
         *        │
         *        ▼
         *     Usuario
         *        │
         *        ▼
         *     UsuarioPrincipal
         *        │
         *        ▼
         *     UserDetails
         */
        return new UsuarioPrincipal(usuario);
    }
}
