package fr.medilabo.microservice.auth.unitaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.medilabo.microservice.auth.enums.RoleEnum;
import fr.medilabo.microservice.auth.errors.BadCredentialsException;
import fr.medilabo.microservice.auth.models.LoginResponseDTO;
import fr.medilabo.microservice.auth.models.User;
import fr.medilabo.microservice.auth.models.UserDTO;
import fr.medilabo.microservice.auth.services.JwtTokenService;
import fr.medilabo.microservice.auth.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenService jwtTokenServiceBean;
    @MockBean
    private UserService userService;
    ObjectMapper objectMapper = new ObjectMapper();
    private final String oldToke = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJNZWRpX0xhYm9fQXBwbGkiLCJpYXQiOjE3MTA5OTc0MjgsImV4cCI6MTcxMDk5NzQyOCwic3ViIjoidGVzdEBnbWFpbC5jb20iLCJSb2xlIjoiT1JHQU5JU0FURVVSIn0.Cyk8khHC3UoJZp1RT3ylNZBoXnkUX7vYR2-U_LYfUQM";
    private static User user;

    @BeforeAll
    public static void setToken(){
        user = new User(1L, "test", "test", "test@gmail.com", "123456789", "123456789", RoleEnum.ORGANISATEUR);
    }

    @Test
    public void loginTest() throws Exception{
        UserDTO userDTO = new UserDTO("test", "test");
        LoginResponseDTO responseDTO = new LoginResponseDTO("test", RoleEnum.ORGANISATEUR.getRole());
        Mockito.when(userService.authenticateUser(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/auth/login").content(objectMapper.writeValueAsString(userDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.token", containsStringIgnoringCase("test"))
                );
    }

    @Test
    public void loginErrorTest() throws Exception{
        UserDTO userDTO = new UserDTO("test", "test");
        Mockito.when(userService.authenticateUser(any())).thenThrow(new BadCredentialsException("Incorrect username or Password"));

        mockMvc.perform(post("/auth/login").content(objectMapper.writeValueAsString(userDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    public void updateTokenTest() throws Exception{
        Mockito.when(jwtTokenServiceBean.getExpireDateFromToken(anyString())).thenReturn(new Date(new Date().getTime() + 100000));
        Mockito.when(jwtTokenServiceBean.getSubjectFromToken(anyString())).thenReturn("test@mail.com");
        Mockito.when(jwtTokenServiceBean.getRoleFromToken(anyString())).thenReturn(RoleEnum.ORGANISATEUR.getRole());
        Mockito.when(jwtTokenServiceBean.generateToken(any())).thenReturn(oldToke);

        mockMvc.perform(post("/auth/update").content(objectMapper.writeValueAsString(oldToke)).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", is(oldToke))
                );

        Mockito.verify(jwtTokenServiceBean, Mockito.times(1)).getExpireDateFromToken(anyString());
        Mockito.verify(jwtTokenServiceBean, Mockito.times(1)).getSubjectFromToken(anyString());
        Mockito.verify(jwtTokenServiceBean, Mockito.times(1)).getRoleFromToken(anyString());
        Mockito.verify(jwtTokenServiceBean, Mockito.times(1)).generateToken(any());
    }

    @Test
    public void updateExpiredTokenTest() throws Exception{
        Mockito.when(jwtTokenServiceBean.getExpireDateFromToken(anyString())).thenReturn(new Date(new Date().getTime() - 100000));
        Mockito.when(jwtTokenServiceBean.getSubjectFromToken(anyString())).thenReturn("test@mail.com");
        Mockito.when(jwtTokenServiceBean.getRoleFromToken(anyString())).thenReturn(RoleEnum.ORGANISATEUR.getRole());
        Mockito.when(jwtTokenServiceBean.generateToken(any())).thenReturn(oldToke);

        mockMvc.perform(post("/auth/update").content(objectMapper.writeValueAsString(oldToke)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        status().isUnauthorized()
                );

        Mockito.verify(jwtTokenServiceBean, Mockito.times(1)).getExpireDateFromToken(anyString());
        Mockito.verify(jwtTokenServiceBean, Mockito.times(0)).getSubjectFromToken(anyString());
        Mockito.verify(jwtTokenServiceBean, Mockito.times(0)).getRoleFromToken(anyString());
        Mockito.verify(jwtTokenServiceBean, Mockito.times(0)).generateToken(any());
    }
}
