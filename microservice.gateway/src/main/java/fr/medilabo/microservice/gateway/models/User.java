package fr.medilabo.microservice.gateway.models;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import fr.medilabo.microservice.gateway.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@RedisHash(value = "user", timeToLive = 60 * 60 * 4)
public class User {

    @Id
    private String token;
    private String ip;
    private Date expirationDate;
}
