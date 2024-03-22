package fr.medilabo.microservice.auth.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import fr.medilabo.microservice.auth.enums.RoleEnum;
import fr.medilabo.microservice.auth.errors.BadCredentialsException;
import fr.medilabo.microservice.auth.models.LoginResponseDTO;
import fr.medilabo.microservice.auth.models.User;
import fr.medilabo.microservice.auth.models.UserDTO;
import fr.medilabo.microservice.auth.services.JwtTokenService;
import fr.medilabo.microservice.auth.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    private Logger logger = LoggerFactory.getLogger(AuthUserController.class);
    @Autowired
    private UserService service;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping
    public String testHealthy(){
        return "ok";
    }
    @PostMapping("/update")
    public ResponseEntity<String> tokenUpdate(@RequestBody String token, HttpServletRequest request){
        logger.info("Call tokenUpdate - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        try{
            logger.info("token: " + token);
            StringBuilder sb = new StringBuilder(token);
            if(sb.charAt(0) == '"' && sb.charAt(sb.length()-1) == '"'){
                sb.replace(0,1, "");
                sb.replace(sb.length()-1, sb.length(), "");
                token = sb.toString();
            }
            Date expiredDate = jwtTokenService.getExpireDateFromToken(token);
            if (expiredDate.after(new Date())){
                User user = new User();
                user.setEmail(jwtTokenService.getSubjectFromToken(token));
                user.setRole(RoleEnum.valueOf(jwtTokenService.getRoleFromToken(token)));
                return ResponseEntity.ok(jwtTokenService.generateToken(user));
            }
        } catch (JWTVerificationException e){
            logger.warn("Token invalid or expired");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody User user){
        if(service.findOneByEmail(user.getEmail()).isEmpty()){
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
            return  ResponseEntity.ok(service.save(user));
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserDTO userDTO, HttpServletResponse response, HttpServletRequest request){
        logger.info("Call login - ip (" + request.getRemoteAddr() +") - token (" + request.getHeader("Authorization") +")");
        try{
            LoginResponseDTO responseDTO = service.authenticateUser(userDTO);
            response.setHeader("Authorization", responseDTO.token());
            return ResponseEntity.ok(responseDTO);
        }catch (BadCredentialsException e){
            logger.info("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
