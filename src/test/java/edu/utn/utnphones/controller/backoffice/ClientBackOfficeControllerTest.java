package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.UserController;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientBackOfficeControllerTest {


    User user;
    ClientBackOfficeController clientBackOfficeController;
    @Mock
    private UserController userController;

    @Before
    public void setUp() {
        initMocks(this);
        clientBackOfficeController = new ClientBackOfficeController(userController);
        //ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        user = new User();
        user.setDni("4333444");
    }
/*
        @Test
        void addNewClient() throws UserAlreadyExistsException, ValidationException, CityNotFoundException {
            User user = new User((long) 1, "4333444", "santiago", "labatut", "pwd123", true, Role.client, null);
            ClientRequestDto clientRequestDto = new ClientRequestDto((long) 1, "Santiago", "labatut", "4333444", TypeLine.home);

            when(userController.addClient(clientRequestDto)).thenReturn(user);
            URI uri = clientBackOfficeController.getLocation(user);
            ResponseEntity<User> response = ResponseEntity.created(uri).build();
            ResponseEntity<User> response1 = clientBackOfficeController.addNewClient(clientRequestDto);

            assertEquals(response,response1.getStatusCodeValue());

        }*/

    @Test
    public void getClientByDniOk() throws UserNotFoundException, ValidationException {
        when(userController.getClientByDni("4333444")).thenReturn(user);
        ResponseEntity response = ResponseEntity.ok(user);
        ResponseEntity<User> response1 = clientBackOfficeController.getClientByDni("4333444");
        assertEquals(response, response1);
        assertEquals(response.getStatusCodeValue(),200);
    }

    @Test
    public void getClientByDniException() throws UserNotFoundException, ValidationException {

        when(userController.getClientByDni("4333444")).thenThrow((new UserNotFoundException()));

        assertThrows(UserNotFoundException.class, () -> {clientBackOfficeController.getClientByDni("4333444");
        });
    }

    @Test
    public void getAllUsersOk() {
        List<User> clients = new ArrayList<>();
        clients.add(user);
        when(userController.getAllClients()).thenReturn(clients);
        ResponseEntity<List<User>> responseEntity = clientBackOfficeController.getAllUsers();

        assertEquals(responseEntity.getBody().size(),clients.size());
        assertEquals(responseEntity.getBody().get(0).getDni(),clients.get(0).getDni());

    }
    @Test
    public void getAllUsersNoContent() {

        List<User> clients = new ArrayList<>();
        when(userController.getAllClients()).thenReturn(clients);
        ResponseEntity<List<User>> responseEntity = clientBackOfficeController.getAllUsers();
        assertEquals(responseEntity.getStatusCodeValue(),204);
    }

//
//    @Test
//    void updateUserByDniOk() throws UserNotFoundException {
//        User user = new User();
//
//        doNothing().when(userController).updateClient("1234", user);
//        ResponseEntity<User> responseEntity = clientBackOfficeController.updateUserByDni("1234",user);
//        verify(userController, times(1)).updateClient("1234", user);
//
//        assertEquals(responseEntity, ResponseEntity.accepted().build());
//
//    }
//
//    @Test
//    void updateUserByDniException() throws UserNotFoundException {
//        User user = new User();
//        doNothing().when(userController).updateClient("1234", user);
//        when(clientBackOfficeController.updateUserByDni("1234",user)).thenThrow((new JpaSystemException(new RuntimeException(new SQLException()))));
//        ResponseEntity<User> responseEntity = clientBackOfficeController.updateUserByDni("1234",user);
//        assertEquals(responseEntity,ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }
//    @Test
//    public void deleteClientOk() throws UserNotFoundException, ValidationException {
//
//        doNothing().when(userController).deleteClient("1234");
//        ResponseEntity<User> responseEntity = clientBackOfficeController.deleteClient("1234");
//        verify(userController, times(1)).deleteClient("1234");
//        assertEquals(responseEntity, ResponseEntity.ok().build());
//    }
//
//    @Test
//    void deleteClientException() throws UserNotFoundException, ValidationException {
//
//        doNothing().when(userController).deleteClient("1234");
//        when(clientBackOfficeController.deleteClient("1234")).thenThrow((new JpaSystemException(new RuntimeException(new SQLException()))));
//        ResponseEntity<User> responseEntity = clientBackOfficeController.deleteClient("1234");
//        assertEquals(responseEntity,ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }


}