package fr.medilabo.microservice.gateway.filters;

import fr.medilabo.microservice.gateway.models.BackendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthBackendFilter extends AbstractGatewayFilterFactory<Object> {
    private final Logger logger = LoggerFactory.getLogger(AuthBackendFilter.class);
    @Autowired
    private BackendService backendService;

    @Override
    public GatewayFilter apply(final Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            logger.info("Call AuthBackendFiltre");
            exchange.getRequest().mutate().header("Backend-name", backendService.getName());
            exchange.getRequest().mutate().header("Backend-reg-number", backendService.getRegNumber().toString());
            logger.info("Service: " + backendService.getName() + " - Reg Number: " + backendService.getRegNumber());
            return chain.filter(exchange);
        }, 0);
    }
}
