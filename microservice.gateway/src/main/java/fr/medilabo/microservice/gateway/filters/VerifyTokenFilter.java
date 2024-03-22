package fr.medilabo.microservice.gateway.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import fr.medilabo.microservice.gateway.enums.RoleEnum;
import fr.medilabo.microservice.gateway.models.User;
import fr.medilabo.microservice.gateway.services.JwtService;
import fr.medilabo.microservice.gateway.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
public class VerifyTokenFilter extends AbstractGatewayFilterFactory<Object> {

    private final Logger logger = LoggerFactory.getLogger(VerifyTokenFilter.class);
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    private RoleEnum roleRequired;
    @Value("${auth.update}")
    private String URL_AUTH;
    private final String PATH_ORGANISATEUR = "patient";
    private final String[] PATH_PRATICIEN = {"notes", "risques"};


    @Override
    public GatewayFilter apply(final Object config) {
        logger.info("Call VerifyTokenFilter");
        return new OrderedGatewayFilter((exchange, chain) ->{
            ServerHttpRequest req = exchange.getRequest();
            roleRequired = req.getURI().toString().contains(PATH_ORGANISATEUR) ? RoleEnum.ORGANISATEUR : RoleEnum.PRATICIEN;
            if (exchange.getRequest().getHeaders().containsKey("Authorization")) {
                String token = exchange.getRequest().getHeaders().get("Authorization").get(0).toString().replace("Bearer ", "");
                logger.info("Request with token: " + token);
                Optional<User> optionalUser = userService.findById(token);
                if (optionalUser.isPresent()) {
                    try {
                        RoleEnum role = RoleEnum.valueOf(jwtService.getRoleFromToken(token));
                        if (optionalUser.get().getExpirationDate().after(new Date()) && role.equals(roleRequired)) {
                            logger.info("Token Valid");
                            return chain.filter(exchange);
                        } else if (role.equals(roleRequired)) {
                            RestTemplate template = new RestTemplate();
                            String newToken = template.postForEntity(URL_AUTH, token, String.class).getBody().replace("Bearer ", "");
                            logger.info("Token expired - call Auth update token - New Token: " + newToken);
                            if (!newToken.isEmpty()) {
                                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                                    User user = new User(newToken, exchange.getRequest().getRemoteAddress().toString(), jwtService.halfTimeExpiredToken(newToken));
                                    userService.save(user);
                                    exchange.getResponse().getHeaders().setAccessControlExposeHeaders(Arrays.asList("Authorization_update"));
                                    exchange.getResponse().getHeaders().add("Authorization_update", "Bearer " + newToken);
                                }));
                            }
                        }
                    } catch (JWTVerificationException e) {
                        logger.warn("Jwt is not valid");
                    }
                }
            }
            logger.warn("Jwt is not in the request");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }, 1);
    }
}
