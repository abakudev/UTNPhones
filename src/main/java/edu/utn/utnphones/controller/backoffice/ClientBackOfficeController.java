package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.UserController;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.ClientRequestDto;
import edu.utn.utnphones.exceptions.CityNotFoundException;
import edu.utn.utnphones.exceptions.UserAlreadyExistsException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.utils.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/backoffice/clients")
public class ClientBackOfficeController {

    private final UserController userController;

    @Autowired
    public ClientBackOfficeController(UserController userController) {
        this.userController = userController;
    }

    @PostMapping
    public ResponseEntity<User> addNewClient(@RequestBody ClientRequestDto newClient) throws UserAlreadyExistsException, ValidationException, CityNotFoundException, NoSuchAlgorithmException {

        User user = userController.addClient(newClient);
        return ResponseEntity.created(UriUtils.getLocation(user.getUserId())).build();
    }

    @GetMapping("/{dni}")
    public ResponseEntity<User> getClientByDni(@PathVariable(value = "dni") String dni) throws ValidationException, UserNotFoundException {

        User client = userController.getClientByDni(dni);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> clients = userController.getAllClients();
        return (clients.size() != 0) ? ResponseEntity.ok(clients) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{dni}")
    public ResponseEntity<User> updateUserByDni(@PathVariable(value = "dni") String dni , @RequestBody ClientRequestDto newClient) throws UserNotFoundException, CityNotFoundException, ValidationException {
        userController.updateClient(dni, newClient);
        return ResponseEntity.accepted().build();
    }


    @DeleteMapping("/{dni}")
    public ResponseEntity deleteClient(@PathVariable(value = "dni") String dni ) throws ValidationException, UserNotFoundException {

        userController.deleteClient(dni);
        return ResponseEntity.ok().build();
    }

}
