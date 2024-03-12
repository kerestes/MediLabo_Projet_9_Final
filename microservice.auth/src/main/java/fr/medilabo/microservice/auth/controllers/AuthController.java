package fr.medilabo.microservice.auth.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import fr.medilabo.microservice.auth.enums.RoleEnum;
import fr.medilabo.microservice.auth.errors.BadCredentialsException;
import fr.medilabo.microservice.auth.models.LoginResponseDTO;
import fr.medilabo.microservice.auth.models.User;
import fr.medilabo.microservice.auth.models.UserDTO;
import fr.medilabo.microservice.auth.services.JwtTokenService;
import fr.medilabo.microservice.auth.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService service;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/update")
    public ResponseEntity<String> tokenUpdate(@RequestBody String token){
        try{
            Date expiredDate = jwtTokenService.getExpireDateFromToken(token);
            if (expiredDate.after(new Date())){
                User user = new User();
                user.setEmail(jwtTokenService.getSubjectFromToken(token));
                user.setRole(RoleEnum.valueOf(jwtTokenService.getRoleFromToken(token)));
                String newToken = jwtTokenService.generateToken(user);
                return ResponseEntity.ok(newToken);
            }
        } catch (JWTVerificationException e){

        }
        return ResponseEntity.ok().build();
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

    //Tem que receber so usuario e senha, se nao da ruim
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserDTO userDTO, HttpServletResponse response){
        try{
            LoginResponseDTO responseDTO = service.authenticateUser(userDTO);
            response.setHeader("Authorization", responseDTO.token());
            return ResponseEntity.ok(responseDTO);
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
