package fr.medilabo.microservice.gateway.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;
import reactor.netty.http.server.HttpServerRequest;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "H&EVa6#wm6GMJRBsDSKSnM&&8dp837";

    private static final String ISSUER = "Medi_Labo_Appli";

    private Algorithm key = Algorithm.HMAC256(SECRET_KEY);

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

    public String getRoleFromToken(String token) {
        try {
            return JWT.require(key)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getClaim("Role")
                    .asString();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Invalid or expired token");
        }
    }

    public Date getExpireDateFromToken(String token){
        try {
            return JWT.require(key)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getExpiresAt();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Invalid or expired token");
        }
    }

    public String recoveryToken(HttpServerRequest request) {
        String authorizationHeader = request.requestHeaders().get("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
