package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.enums.Role;
import edu.utn.utnphones.domain.enums.TypeLine;
import edu.utn.utnphones.dto.ClientRequestDto;
import edu.utn.utnphones.exceptions.*;
import edu.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    UserController userController;
    User user;
    ClientRequestDto clientDto;

    @Mock
    UserService userService;

    @Before
    public void setUp() {
        initMocks(this);
        userController = new UserController(userService);
        clientDto = new ClientRequestDto((long) 1, "Agustin", "Salvador", "4333444","123asd", TypeLine.home);
        user = new User((long) 1, "123", "Agustin", "Salvador", "pwd123", true, Role.client, null);
    }

    @Test
    public void loginOk() throws UserNotFoundException, ValidationException, InvalidLoginException, NoSuchAlgorithmException {
        when(userService.login("123","pwd")).thenReturn(user);
        User user1 = userController.login("123","pwd");
        assertEquals(user1,user);
    }
    @Test
    public void loginDniNull() {
        assertThrows(ValidationException.class, () -> userController.login(null,"pwd"));
    }

    @Test
    public void loginPwdNull() throws UserNotFoundException, ValidationException {
        assertThrows(ValidationException.class, () -> userController.login("123",null));
    }
    @Test
    public void loginUserNotFound() throws UserNotFoundException, InvalidLoginException, NoSuchAlgorithmException {
        when(userService.login("123","pwd")).thenThrow(new UserNotFoundException());
        assertThrows(UserNotFoundException.class, () -> userController.login("123","pwd"));
    }

    @Test
    public void loginDniEmpty(){
        assertThrows(ValidationException.class, () -> userController.login("","321"));
    }

    @Test
    public void loginPwdEmpty(){
        assertThrows(ValidationException.class, () -> userController.login("123",""));
    }

    @Test
    public void addClientOk() throws UserAlreadyExistsException, CityNotFoundException, ValidationException, NoSuchAlgorithmException {

        ClientRequestDto clientRequestDto = new ClientRequestDto((long) 1, "Santiago", "labatut", "4333444","123asd", TypeLine.home);
        when(userService.addClient(clientRequestDto)).thenReturn(user);
        User user1 = userController.addClient(clientRequestDto);
        assertEquals(user1,user);
    }

    @Test
    public void addClientUserAlreadyExist() throws UserAlreadyExistsException, CityNotFoundException, ValidationException, NoSuchAlgorithmException {
        ClientRequestDto client = new ClientRequestDto();
        when(userService.addClient(client)).thenThrow((new UserAlreadyExistsException("Already exist")));
    }

    @Test(expected = ValidationException.class)
    public void addClientInvalidData() throws NoSuchAlgorithmException, UserAlreadyExistsException, CityNotFoundException, ValidationException {

        clientDto = new ClientRequestDto((long) 1, "Agustin", "", "4333444","123asd", TypeLine.home);
        when(userService.addClient(clientDto)).thenThrow(ValidationException.class);
        userController.addClient(clientDto);
    }


    @Test
    public void getClientByDniOk() throws UserNotFoundException, ValidationException {
        when(userService.getClientByDni("22")).thenReturn(user);
        User user1 = userController.getClientByDni("22");
        assertEquals(user1,user);
    }

    @Test
    public void getClientByDniEmpty() {
        assertThrows(ValidationException.class, () -> userController.getClientByDni(""));
    }

    @Test
    public void getClientByDniNull() {
        assertThrows(ValidationException.class, () -> userController.getClientByDni(null));
    }

    @Test
    public void getAllClientsOk() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.getAllClients()).thenReturn(users);
        List<User> users2 = userController.getAllClients();
        assertEquals(users.get(0),users2.get(0));
        assertEquals(users.size(),users2.size());
    }

    @Test
    public void updateClientOk() throws UserNotFoundException, CityNotFoundException, ValidationException {
        when(userService.updateClient("123",clientDto)).thenReturn(user);
        User returnedUser = userController.updateClient("123",clientDto);
        assertEquals(user, returnedUser);
        verify(userService, times(1)).updateClient("123",clientDto);
    }

    @Test
    public void updateClientDniNull(){
        assertThrows(ValidationException.class, () -> userController.updateClient(null, clientDto) );
    }

    @Test
    public void updateClientDniEmpty(){
        assertThrows(ValidationException.class, () -> userController.updateClient("", clientDto) );
    }

    @Test
    public void testDeleteClientOk() throws UserNotFoundException, ValidationException {
        doNothing().when(userService).deleteClient("123");
        userController.deleteClient("123");
        verify(userService, times(1)).deleteClient("123");
    }

    @Test
    public void testDeleteClientDniNull(){
        assertThrows(ValidationException.class, () -> userController.deleteClient(null) );
    }

    @Test
    public void testDeleteClientDniEmpty(){
        assertThrows(ValidationException.class, () -> userController.deleteClient("") );
    }

}

