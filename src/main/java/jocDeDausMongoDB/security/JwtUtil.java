package jocDeDausMongoDB.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

public class JwtUtil {

    //public static final long TOKEN_EXPIRATION_TIME = 60000; // 1 min
    public static final long TOKEN_EXPIRATION_TIME = 86400000; // 1 dia

    // Método para crear el JWT y enviarlo al cliente en el header de la respuesta
    static void addAuthentication(HttpServletResponse res, String username) {



        String token = Jwts.builder()
                .setSubject(username)

                // Tiempo de expiracion de 10 minutos
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))

                // Hash con el que firmaremos la clave
                .signWith(SignatureAlgorithm.HS512, "secret@")
                .compact();

        //agregamos al encabezado el token
        res.addHeader("Authorization", "Bearer " + token);

        //agregamos al encabezado el token
        res.addHeader("Authorization", token);
    }

    // Método para validar el token enviado por el cliente
    static Authentication getAuthentication(HttpServletRequest request) {

        // Obtenemos el token que viene en el encabezado de la peticion
        String token = request.getHeader("Authorization");

        // si hay un token presente, entonces lo validamos
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey("secret@")
                    .parseClaimsJws(token.replace("Bearer", "")) //este metodo es el que valida
                    .getBody()
                    .getSubject();

            // Para las demás peticiones que no sean /login
            // no requerimos una autenticacion por name/password
            // por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin password
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }
}