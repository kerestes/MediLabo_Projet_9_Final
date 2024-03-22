package fr.medilabo.microservice.auth.services;

import fr.medilabo.microservice.auth.errors.BadCredentialsException;
import fr.medilabo.microservice.auth.models.LoginResponseDTO;
import fr.medilabo.microservice.auth.models.User;
import fr.medilabo.microservice.auth.models.UserDTO;
import fr.medilabo.microservice.auth.respositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
        logger.info("Call findOneByEmail - User Service");
        return userRepository.findOneByEmail(email);
    }

    public User save(User user){
        logger.info("Call save - User Service");
        return userRepository.save(user);
    }

    public LoginResponseDTO authenticateUser(UserDTO userDTO){
        logger.info("Call authenticateUser - User Service");
        Optional<User> userOptional = userRepository.findOneByEmail(userDTO.username());
        if(userOptional.isPresent() && BCrypt.checkpw(userDTO.password(), userOptional.get().getPassword())){
            String token = jwtTokenService.generateToken(userOptional.get());
            LoginResponseDTO response = new LoginResponseDTO(token, userOptional.get().getRole().getRole());
            return response;
        }else{
            throw new BadCredentialsException("Incorrect username or Password");
        }
    }

}

