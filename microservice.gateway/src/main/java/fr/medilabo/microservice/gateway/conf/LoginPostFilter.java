package fr.medilabo.microservice.gateway.conf;

import fr.medilabo.microservice.gateway.enums.RoleEnum;
import fr.medilabo.microservice.gateway.models.User;
import fr.medilabo.microservice.gateway.services.JwtService;
import fr.medilabo.microservice.gateway.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Configuration
public class LoginPostFilter implements GlobalFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            ServerHttpRequest req = exchange.getRequest();
            if(req.getURI().toString().contains("login") && exchange.getResponse().getStatusCode() != HttpStatus.NOT_FOUND){
                String token = exchange.getResponse().getHeaders().get("Authorization").get(0).replace("Bearer ", "");
                User user = new User(token,
                        exchange.getRequest().getRemoteAddress().toString(),
                        new Date());
                userService.save(user);
                exchange.getResponse().getHeaders().remove("Authorization");
            }
        }));
    }
}
