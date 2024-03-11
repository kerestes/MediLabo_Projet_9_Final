package fr.medilabo.microservice.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import fr.medilabo.microservice.auth.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    private static final String SECRET_KEY = "H&EVa6#wm6GMJRBsDSKSnM&&8dp837";

    private static final String ISSUER = "Medi_Labo_Appli";

    private Algorithm key = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getEmail())
                    .withClaim("Role", user.getRole().getRole())
                    .sign(key);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Internal error in token processing ", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            return JWT.require(key)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Invalid or expired token");
        }
    }

    public String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("Europe/Paris")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("Europe/Paris")).plusHours(4).toInstant();
    }
}

