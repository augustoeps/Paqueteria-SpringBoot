package com.example.paqueteria.infrastructure.config.security;

import com.example.paqueteria.domain.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
 * ============================================================================
 * ¿QUÉ FUNCIÓN TIENE ESTA CLASE?
 * ============================================================================
 *
 * UsuarioPrincipal es un ADAPTADOR entre nuestra entidad de dominio Usuario
 * y Spring Security.
 *
 * Nuestra aplicación tiene su propia clase Usuario, que pertenece al dominio
 * y tiene la estructura que nosotros hemos decidido:
 *
 *     Usuario
 *       ├── username
 *       ├── contraseña
 *       └── rol
 *
 * El problema es que Spring Security no trabaja directamente con nuestra
 * entidad Usuario. Spring Security necesita un objeto que implemente la
 * interfaz UserDetails.
 *
 * UserDetails define los datos que Spring Security necesita conocer sobre
 * un usuario para poder autenticarlo y autorizarlo:
 *
 *     - username
 *     - password
 *     - authorities/roles
 *     - si la cuenta está activa
 *     - si la cuenta está bloqueada
 *     - si la cuenta ha expirado
 *     - si las credenciales han expirado
 *
 * Por eso creamos:
 *
 *     UsuarioPrincipal implements UserDetails
 *
 * Esta clase recibe nuestro Usuario y "traduce" sus datos al formato que
 * Spring Security entiende.
 *
 *
 * Podemos verlo de esta manera:
 *
 *
 *                  NUESTRA APLICACIÓN
 *
 *                       Usuario
 *                          │
 *                          │
 *                          ▼
 *                  UsuarioPrincipal
 *                          │
 *                          │ implements
 *                          ▼
 *                     UserDetails
 *                          │
 *                          ▼
 *                  Spring Security
 *
 *
 * Por ejemplo, nuestro Usuario puede tener:
 *
 *     usuario.getUsuarioUsername().getValor()
 *
 * mientras que Spring Security espera:
 *
 *     getUsername()
 *
 * UsuarioPrincipal conecta ambas cosas.
 *
 * Por tanto, esta clase NO representa un usuario diferente.
 * Es una representación de nuestro Usuario adaptada a Spring Security.
 *
 * ============================================================================
 */
public class UsuarioPrincipal implements UserDetails {

    /*
     * Guardamos dentro de UsuarioPrincipal el Usuario real de nuestra
     * aplicación.
     *
     * De esta manera no necesitamos copiar todos sus datos.
     *
     * UsuarioPrincipal simplemente "envuelve" al Usuario y posteriormente
     * utiliza sus datos para responder a las preguntas que Spring Security
     * realizará mediante UserDetails.
     *
     * Por ejemplo:
     *
     *     UsuarioPrincipal
     *            │
     *            └── Usuario
     *                  ├── username
     *                  ├── contraseña
     *                  └── rol
     */
    private final Usuario usuario;


    /*
     * ========================================================================
     * CONSTRUCTOR
     * ========================================================================
     *
     * Recibe nuestro Usuario y lo guarda dentro de UsuarioPrincipal.
     *
     * Por ejemplo:
     *
     *     Usuario usuario = ...;
     *
     *     UsuarioPrincipal principal =
     *             new UsuarioPrincipal(usuario);
     *
     * A partir de este momento, principal tiene acceso a los datos del
     * Usuario y puede utilizarlos para implementar los métodos de
     * UserDetails que necesita Spring Security.
     */
    public UsuarioPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }


    /*
     * ========================================================================
     * getUsuario()
     * ========================================================================
     *
     * Este método devuelve el objeto Usuario original que hemos guardado.
     *
     * IMPORTANTE:
     *
     * Este método NO pertenece a la interfaz UserDetails.
     * Lo hemos creado nosotros.
     *
     * ¿Por qué puede ser útil?
     *
     * Porque en algún momento de nuestra aplicación podemos tener un
     * UsuarioPrincipal y necesitar recuperar nuestro Usuario de dominio.
     *
     * Ejemplo:
     *
     *     Usuario usuario = principal.getUsuario();
     *
     * De esta forma podemos acceder al objeto de dominio original.
     */
    public Usuario getUsuario() {
        return usuario;
    }


    /*
     * ========================================================================
     * getAuthorities()
     * ========================================================================
     *
     * Este método pertenece a UserDetails y sirve para indicar a Spring
     * Security qué ROLES o AUTORIDADES tiene el usuario.
     *
     * Nuestro Usuario tiene un rol propio de nuestra aplicación:
     *
     *     usuario.getUsuarioRol()
     *
     * Por ejemplo, podría devolver:
     *
     *     ADMIN
     *
     * Después utilizamos .name() para convertir el enum a String:
     *
     *     usuario.getUsuarioRol().name()
     *
     * Resultado:
     *
     *     "ADMIN"
     *
     * Pero Spring Security utiliza normalmente el prefijo "ROLE_" cuando
     * trabajamos con roles.
     *
     * Por eso hacemos:
     *
     *     "ROLE_" + usuario.getUsuarioRol().name()
     *
     * Resultado:
     *
     *     "ROLE_ADMIN"
     *
     *
     * Después creamos una autoridad de Spring Security:
     *
     *     new SimpleGrantedAuthority("ROLE_ADMIN")
     *
     *
     * ¿Por qué hacemos esto?
     *
     * Porque Spring Security necesita representar los permisos/roles
     * mediante objetos GrantedAuthority.
     *
     *
     * El proceso completo sería:
     *
     *     Usuario
     *        │
     *        ▼
     *     usuario.getUsuarioRol()
     *        │
     *        ▼
     *     ADMIN
     *        │
     *        ▼
     *     "ROLE_" + ADMIN
     *        │
     *        ▼
     *     ROLE_ADMIN
     *        │
     *        ▼
     *     SimpleGrantedAuthority
     *        │
     *        ▼
     *     Spring Security
     *
     *
     * Esto permite posteriormente utilizar cosas como:
     *
     *     hasRole("ADMIN")
     *
     * Spring Security buscará internamente una autoridad:
     *
     *     ROLE_ADMIN
     *
     *
     * El método devuelve una Collection porque un usuario puede tener
     * múltiples autoridades.
     *
     * En nuestro caso solamente estamos dando una:
     *
     *     ROLE_ADMIN
     *
     * Por eso utilizamos:
     *
     *     List.of(...)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        /*
         * Obtenemos el rol de nuestro Usuario y le añadimos el prefijo
         * ROLE_ que utiliza Spring Security para identificar roles.
         */
        String rolConPrefijo = "ROLE_" + usuario.getUsuarioRol().name();

        /*
         * Convertimos el String "ROLE_ADMIN", por ejemplo, en una autoridad
         * que Spring Security pueda utilizar.
         */
        return List.of(new SimpleGrantedAuthority(rolConPrefijo));
    }


    /*
     * ========================================================================
     * getPassword()
     * ========================================================================
     *
     * Este método pertenece a UserDetails y le dice a Spring Security
     * cuál es la contraseña almacenada del usuario.
     *
     * En nuestro dominio no tenemos simplemente:
     *
     *     String password;
     *
     * Tenemos un objeto relacionado con la contraseña:
     *
     *     usuario.getUsuarioContrasenaHash()
     *
     * y después obtenemos el hash:
     *
     *     .getHash()
     *
     *
     * Es decir:
     *
     *     Usuario
     *       │
     *       ▼
     *     UsuarioContrasenaHash
     *       │
     *       ▼
     *     getHash()
     *       │
     *       ▼
     *     "$2a$10$..."
     *
     *
     * IMPORTANTE:
     *
     * Aquí no estamos obteniendo la contraseña original en texto plano.
     * Estamos obteniendo el HASH de la contraseña almacenado en nuestra
     * aplicación.
     *
     * Spring Security utilizará ese hash para comprobar las credenciales
     * introducidas durante el proceso de autenticación.
     */
    @Override
    public String getPassword() {
        return usuario.getUsuarioContrasenaHash().getHash();
    }


    /*
     * ========================================================================
     * getUsername()
     * ========================================================================
     *
     * Este método pertenece a UserDetails y le dice a Spring Security
     * cuál es el identificador/username del usuario.
     *
     * Nuestro dominio utiliza:
     *
     *     usuario.getUsuarioUsername()
     *
     * que aparentemente es un objeto de valor y no directamente un String.
     *
     * Por eso necesitamos:
     *
     *     .getValor()
     *
     * para obtener finalmente el String.
     *
     *
     * Por ejemplo:
     *
     *     usuario.getUsuarioUsername()
     *                 │
     *                 ▼
     *           UsuarioUsername
     *                 │
     *                 ▼
     *             getValor()
     *                 │
     *                 ▼
     *             "juan123"
     *
     *
     * Spring Security recibirá:
     *
     *     "juan123"
     *
     * Este método es importante porque Spring Security utiliza el username
     * para identificar al usuario durante la autenticación.
     */
    @Override
    public String getUsername() {
        return usuario.getUsuarioUsername().getValor();
    }


    /*
     * ========================================================================
     * isAccountNonExpired()
     * ========================================================================
     *
     * Este método pertenece a UserDetails y sirve para indicar si la cuenta
     * del usuario ha EXPIRADO.
     *
     * Spring Security permite controlar cuentas que solamente sean válidas
     * durante un periodo determinado.
     *
     * Por ejemplo:
     *
     *     Cuenta válida
     *        │
     *        ▼
     *     01/01/2026
     *        │
     *        ▼
     *     31/12/2026
     *
     * Si la cuenta hubiera expirado, podríamos devolver:
     *
     *     false
     *
     * y Spring Security podría impedir la autenticación.
     *
     * En nuestra aplicación actualmente no estamos controlando la
     * expiración de la CUENTA del usuario.
     *
     * Por eso devolvemos siempre:
     *
     *     true
     *
     * IMPORTANTE:
     *
     * Esto es diferente de la expiración del JWT.
     *
     * El JWT tiene su propia fecha de expiración mediante:
     *
     *     .expiration(...)
     *
     * Este método controla la expiración de la cuenta del usuario,
     * no la expiración del token.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    /*
     * ========================================================================
     * isAccountNonLocked()
     * ========================================================================
     *
     * Este método indica si la cuenta del usuario está BLOQUEADA.
     *
     * Una aplicación podría bloquear una cuenta después de varios intentos
     * incorrectos de inicio de sesión.
     *
     * Por ejemplo:
     *
     *     Intento 1 → contraseña incorrecta
     *     Intento 2 → contraseña incorrecta
     *     Intento 3 → contraseña incorrecta
     *     Intento 4 → contraseña incorrecta
     *                       │
     *                       ▼
     *                  Cuenta bloqueada
     *
     * En ese caso podríamos tener:
     *
     *     isAccountNonLocked() → false
     *
     * Actualmente nuestra entidad Usuario no está utilizando una lógica
     * de bloqueo de cuenta, por lo que devolvemos:
     *
     *     true
     *
     * Esto significa:
     *
     *     "La cuenta no está bloqueada."
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /*
     * ========================================================================
     * isCredentialsNonExpired()
     * ========================================================================
     *
     * Este método indica si las CREDENCIALES del usuario siguen siendo
     * válidas.
     *
     * Las credenciales normalmente hacen referencia a información como
     * la contraseña.
     *
     * Una aplicación podría establecer una política como:
     *
     *     "La contraseña debe cambiarse cada 90 días."
     *
     * Cuando esa contraseña ya no fuera válida, este método podría devolver:
     *
     *     false
     *
     * y Spring Security podría impedir el acceso.
     *
     * En nuestra aplicación todavía no estamos controlando la expiración
     * de las credenciales.
     *
     * Por eso devolvemos:
     *
     *     true
     *
     * Significa:
     *
     *     "Las credenciales del usuario siguen siendo válidas."
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /*
     * ========================================================================
     * isEnabled()
     * ========================================================================
     *
     * Este método indica si el usuario está HABILITADO para utilizar
     * la aplicación.
     *
     * Por ejemplo, podríamos tener:
     *
     *     Usuario Juan → activo
     *     Usuario Pedro → desactivado
     *
     * Para Pedro podríamos devolver:
     *
     *     false
     *
     * y Spring Security impediría su autenticación.
     *
     * En nuestra aplicación actualmente no estamos utilizando un campo
     * para indicar si el usuario está habilitado o deshabilitado.
     *
     * Por eso devolvemos:
     *
     *     true
     *
     * Esto significa:
     *
     *     "El usuario está habilitado."
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
