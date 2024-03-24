package fr.medilabo.microservice.gateway.filters;

import fr.medilabo.microservice.gateway.models.User;
import fr.medilabo.microservice.gateway.services.JwtService;
import fr.medilabo.microservice.gateway.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Configuration
public class LoginPostFilter implements GlobalFilter {
    private final Logger logger = LoggerFactory.getLogger(LoginPostFilter.class);
    @Autowired
    private UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            if(exchange.getRequest().getURI().toString().contains("auth/login") && exchange.getResponse().getStatusCode() != HttpStatus.NOT_FOUND){
                logger.info("Call LoginPostFilter");
                String token = exchange.getResponse().getHeaders().get("Authorization").get(0).replace("Bearer ", "");
                User user = new User(token,
                        exchange.getRequest().getRemoteAddress().toString(),
                        new Date());
                userService.save(user);
                logger.info("Token: " + token + " - successfully registered");
                exchange.getResponse().getHeaders().remove("Authorization");
            }
        }));
    }
}
