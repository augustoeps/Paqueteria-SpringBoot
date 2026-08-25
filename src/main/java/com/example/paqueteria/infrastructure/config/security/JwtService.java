package com.example.paqueteria.infrastructure.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

    private final SecretKey secretKey;
    private final long expiracionMs;

    /*
     * Constructor del servicio JWT.
     *
     * Recibe el secreto utilizado para firmar los tokens y el tiempo
     * de expiración del token desde application.properties
     *
     * El secreto se convierte en una SecretKey que posteriormente se
     * utilizará para firmar y validar la autenticidad del JWT.
     */
    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiracionMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiracionMs = expiracionMs;
    }

    /*
     * Genera un nuevo token JWT para un usuario.
     *
     * .subject(username)
     * Guarda el username en el campo estándar "sub" (subject) del JWT.
     * No estamos creando un claim personalizado llamado "username".
     * JWT ya tiene un campo estándar "sub" pensado precisamente para
     * identificar al sujeto del token.
     *
     * .claim("rol", rol)
     * Añade un claim personalizado llamado "rol".
     * Como "rol" no es uno de los campos estándar que estamos utilizando,
     * nosotros elegimos el nombre "rol" y guardamos ahí su valor.
     *
  .
     *
     * .issuedAt(...)
     * Guarda la fecha en la que se creó el token.
     *
     * .expiration(...)
     * Define cuándo dejará de ser válido el token.
     *
     * .signWith(secretKey)
     * Firma el token con nuestra clave secreta para poder comprobar
     * posteriormente que el token no ha sido manipulado.
     */
    public String generarToken(String username, String rol) {
        return Jwts.builder()
                .subject(username)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiracionMs))
                .signWith(secretKey)
                .compact();
    }

    /*
     * Extrae el username del token.
     *
     * El username se guardó utilizando:
     *
     *     .subject(username)
     *
     * Por tanto, no necesitamos buscar un claim llamado "username".
     * El username está dentro del campo estándar "sub" del JWT.
     *
     * Claims::getSubject equivale a:
     *
     *     claims -> claims.getSubject()
     *
     * Es decir, obtenemos el valor del campo "sub".
     */
    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    /*
     * Extrae el rol del token.
     *
     * El rol fue guardado como un claim personalizado:
     *
     *     .claim("rol", rol)
     *
     * Por eso ahora debemos indicar explícitamente el nombre del claim
     * que queremos recuperar: "rol".
     *
     * claims.get("rol", String.class)
     * busca dentro del JWT un campo llamado "rol" y espera que su valor
     * sea de tipo String.
     *
     * Esto es diferente de extraer el username porque el username está
     * en el campo estándar "sub", mientras que "rol" es un claim que
     * nosotros hemos creado.
     */
    public String extraerRol(String token) {
        return extraerClaim(token, claims -> claims.get("rol", String.class));
    }

    /*
     * Método genérico para extraer cualquier dato del JWT.
     *
     * Primero se analiza el token y se comprueba su firma utilizando
     * nuestra secretKey.
     *
     * Después se obtiene el Payload, que contiene los Claims del token.
     *
     * El parámetro "resolver" indica qué información queremos extraer.
     *
     * Por ejemplo:
     *
     *     Claims::getSubject
     *
     * significa "dame el subject".
     *
     * Mientras que:
     *
     *     claims -> claims.get("rol", String.class)
     *
     * significa "dame el claim personalizado llamado rol".
     *
     * Gracias a este método no tenemos que repetir toda la lógica de
     * lectura y validación del JWT cada vez que queramos obtener un dato.
     */
    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);
    }

    /*
     * Comprueba si el token pertenece al usuario indicado y además
     * comprueba que todavía no haya expirado.
     *
     * Primero extraemos el username almacenado en el campo estándar
     * "sub" del token.
     *
     * Después lo comparamos con el username que estamos esperando.
     *
     * Finalmente comprobamos que la fecha de expiración todavía no
     * haya pasado.
     *
     * Las dos condiciones deben cumplirse para considerar válido
     * el token.
     */
    public boolean esTokenValido(String token, String username) {
        String usernameDelToken = extraerUsername(token);

        return usernameDelToken.equals(username) && !estaExpirado(token);
    }

    /*
     * Comprueba si el token ha expirado.
     *
     * La fecha de expiración se guarda automáticamente en el claim
     * estándar "exp" mediante:
     *
     *     .expiration(...)
     *
     * Por eso podemos utilizar:
     *
     *     Claims::getExpiration
     *
     * para recuperar esa fecha.
     *
     * Si la fecha de expiración es anterior a la fecha actual,
     * significa que el token ya ha expirado.
     */
    private boolean estaExpirado(String token) {
        Date expiracion = extraerClaim(token, Claims::getExpiration);

        return expiracion.before(new Date());
    }
}
