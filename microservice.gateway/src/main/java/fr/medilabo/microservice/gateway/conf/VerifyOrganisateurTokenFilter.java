package fr.medilabo.microservice.gateway.conf;

import com.auth0.jwt.exceptions.JWTVerificationException;
import fr.medilabo.microservice.gateway.enums.RoleEnum;
import fr.medilabo.microservice.gateway.models.User;
import fr.medilabo.microservice.gateway.services.JwtService;
import fr.medilabo.microservice.gateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

@Component
public class VerifyOrganisateurTokenFilter implements GatewayFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    private RoleEnum roleRequired = RoleEnum.ORGANISATEUR;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if(exchange.getRequest().getHeaders().containsKey("Authorization")){
            String token = exchange.getRequest().getHeaders().get("Authorization").get(0).toString().replace("Bearer ", "");
            System.out.println(token);
            Optional<User> optionalUser =  userService.findById(token);
            if(optionalUser.isPresent()){
                try{
                    RoleEnum role = RoleEnum.valueOf(jwtService.getRoleFromToken(token));
                    if(optionalUser.get().getExpirationDate().after(new Date()) && role.equals(roleRequired)){
                        return chain.filter(exchange);
                    }
                } catch (JWTVerificationException e){
                    System.out.println("erro jwt presente no banco mas nao valido");
                }
            }
        }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
