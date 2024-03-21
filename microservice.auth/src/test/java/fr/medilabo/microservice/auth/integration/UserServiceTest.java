package fr.medilabo.microservice.auth.integration;

import fr.medilabo.microservice.auth.enums.RoleEnum;
import fr.medilabo.microservice.auth.errors.BadCredentialsException;
import fr.medilabo.microservice.auth.models.LoginResponseDTO;
import fr.medilabo.microservice.auth.models.User;
import fr.medilabo.microservice.auth.models.UserDTO;
import fr.medilabo.microservice.auth.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findOneByEMailTest(){
        Optional<User> optionalUser = userService.findOneByEmail("medecin@mail.com");

        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals("Medecin", optionalUser.get().getNom());
    }

    @Test
    public void findOneByEMailNullTest(){
        Optional<User> optionalUser = userService.findOneByEmail("medecin@email.com");

        Assertions.assertTrue(optionalUser.isEmpty());
    }

    @Test
    public void saveTest(){
        User user = new User();
        user.setRole(RoleEnum.ORGANISATEUR);
        user.setEmail("test@mail.com");
        user.setNom("test");
        user.setPrenom("test");
        user.setPassword("123123123");
        user.setConfirmPassword("123123123");

        User savedUser = userService.save(user);

        Assertions.assertTrue(savedUser.getId() != null);

        Optional<User> findUser = userService.findOneByEmail("test@mail.com");

        Assertions.assertTrue(findUser.isPresent());
    }

    @Test
    public void authenticateUserTest(){
        UserDTO userDTO = new UserDTO("medecin@mail.com", "123456789");
        LoginResponseDTO responseDTO = userService.authenticateUser(userDTO);

        Assertions.assertEquals(RoleEnum.PRATICIEN.getRole(), responseDTO.role());
    }

    @Test
    public void authenticateUserErrorTest(){
        UserDTO userDTO = new UserDTO("none@mail.com", "123456789");
        Throwable e = Assertions.assertThrows(BadCredentialsException.class, () -> userService.authenticateUser(userDTO));
        Assertions.assertEquals("Incorrect username or Password", e.getMessage());
    }
}
