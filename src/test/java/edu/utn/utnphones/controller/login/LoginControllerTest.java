package edu.utn.utnphones.controller.login;

import edu.utn.utnphones.controller.UserController;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.enums.Role;
import edu.utn.utnphones.dto.LoginRequestDto;
import edu.utn.utnphones.exceptions.InvalidLoginException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class LoginControllerTest {

    LoginController loginController;
    @Mock
    SessionManager sessionManager;
    @Mock
    private UserController userController;

    @Before
    public void setUp() {
        initMocks(this);
        loginController = new LoginController(userController, sessionManager);
    }

    @Test
    public void testLoginOk() throws ValidationException, InvalidLoginException, NoSuchAlgorithmException, UserNotFoundException {
        User user = new User((long)1, "38686701", "Abel", "Acuña", "abel123", true, Role.client,  null);
        LoginRequestDto loginDto = new LoginRequestDto("38686701", "abel123");
        when(userController.login("38686701", "abel123")).thenReturn(user);
        String token = sessionManager.createSession(user);

        ResponseEntity responseEntity = ResponseEntity.ok().headers(createHeaders(token)).build();
        ResponseEntity response = loginController.login(loginDto);

        assertNotNull(response);
        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }


    @Test
    public void loginBad() throws UserNotFoundException, ValidationException, InvalidLoginException, NoSuchAlgorithmException {
        LoginRequestDto loginRequestDto = new LoginRequestDto("38686701", "abel123");
        when(userController.login("38686701", "abel123")).thenThrow(new UserNotFoundException());

        assertThrows(InvalidLoginException.class, () -> {
            loginController.login(loginRequestDto);
        });
    }

    @Test
    public void logoutOk() {
        User user = new User((long)1, "Agustin", "Salvador", "41686701", "agus", true, Role.client,  null);
        String token = sessionManager.createSession(user);
        doNothing().when(sessionManager).removeSession(token);
        ResponseEntity response = loginController.logout(token);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }
}