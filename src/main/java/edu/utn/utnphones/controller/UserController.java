package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.ClientRequestDto;
import edu.utn.utnphones.exceptions.*;
import edu.utn.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User login(String dni, String password) throws UserNotFoundException, ValidationException, InvalidLoginException, NoSuchAlgorithmException {
        if ( (dni != null) &&(!dni.isEmpty()) && (password != null)&&(!password.isEmpty()) ){
            return userService.login(dni, password);
        } else {
            throw new ValidationException("dni and password must have a value");
        }
    }

    public User addClient (ClientRequestDto newClient) throws UserAlreadyExistsException, ValidationException, CityNotFoundException, NoSuchAlgorithmException {
        if(!newClient.isValid()) throw new ValidationException("Error - does not include all necessary information ");
        return userService.addClient(newClient);
    }

    public User getClientByDni(String dni) throws UserNotFoundException, ValidationException {
        if ((dni != null) &&(!dni.isEmpty()) ) {
            return userService.getClientByDni(dni);
        } else {
            throw new ValidationException("dni must have a value");
        }
    }

    public List<User> getAllClients()throws JpaSystemException{
        return userService.getAllClients();
    }

    public User updateClient(String dni, ClientRequestDto newClient) throws UserNotFoundException, CityNotFoundException, ValidationException {
        if ((dni != null) &&(!dni.isEmpty()) ) {
            return userService.updateClient(dni, newClient);
        } else {
            throw new ValidationException("dni must have a value");
        }
    }

    public void deleteClient(String  dni) throws ValidationException, UserNotFoundException {
        if ((dni != null) &&(!dni.isEmpty()) ) {
            userService.deleteClient(dni);
        } else {
            throw new ValidationException("dni must have a value");
        }
    }

}
