package fr.medilabo.microservice.auth.services;

import fr.medilabo.microservice.auth.controllers.AuthController;
import fr.medilabo.microservice.auth.errors.BadCredentialsException;
import fr.medilabo.microservice.auth.models.LoginResponseDTO;
import fr.medilabo.microservice.auth.models.User;
import fr.medilabo.microservice.auth.models.UserDTO;
import fr.medilabo.microservice.auth.respositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenService jwtTokenService;

    public Optional<User> findOneByEmail(String email){
        return userRepository.findOneByEmail(email);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public LoginResponseDTO authenticateUser(UserDTO userDTO){
        logger.info("Start of user authentication");
        Optional<User> userOptional = userRepository.findOneByEmail(userDTO.username());
        if(userOptional.isPresent() && BCrypt.checkpw(userDTO.password(), userOptional.get().getPassword())){
            logger.info("User found");
            String token = jwtTokenService.generateToken(userOptional.get());
            LoginResponseDTO response = new LoginResponseDTO("Bearer " + token, userOptional.get().getRole().getRole());
            return response;
        }else{
            logger.info("User not found");
            throw new BadCredentialsException("Incorrect username or Password");
        }
    }

}

