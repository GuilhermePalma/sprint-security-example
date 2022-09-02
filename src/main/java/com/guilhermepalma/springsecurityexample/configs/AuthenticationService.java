package com.guilhermepalma.springsecurityexample.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.guilhermepalma.springsecurityexample.database.models.User;
import com.guilhermepalma.springsecurityexample.utis.Utils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class AuthenticationService {

    static private final long EXPIRATION_TIME = 86400000; // 24hr
    static private final String SCHEME_HTTP_AUTH = "Bearer";
    static private final String SECRET_KEY = "fsafs-fasf-agaosga-sga-r12r-r1";

    static public void addToken(HttpServletResponse response, User user) {
        response.addHeader("Authentication", SCHEME_HTTP_AUTH + " " + createJWT(user));
        // Define que o Token só seja acessado em Autorizações (Prevenção de acesso via Javascript)
        response.addHeader("Acess-Control-Expose-Headers", "Authorization");
    }

    static public Authentication getAuthentication(HttpServletRequest request) {
        try {
            String cryptographJWT = request.getHeader("Authentication");
            if (Utils.isNullOrEmpty(cryptographJWT)) {
                return null;
            }

            cryptographJWT = cryptographJWT.replace((SCHEME_HTTP_AUTH + " "), "");
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(cryptographJWT);

            UUID userId = UUID.fromString(decodedJWT.getClaim("userId").asString());
            String username = decodedJWT.getSubject();
            User user = new User(userId, username);

            return Utils.isNullOrEmpty(username) ? null : new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        } catch (Exception ex) {
            return null;
        }
    }

    static private String createJWT(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId().toString())
                .withExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_TIME).toInstant((ZoneOffset.UTC)))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
}
