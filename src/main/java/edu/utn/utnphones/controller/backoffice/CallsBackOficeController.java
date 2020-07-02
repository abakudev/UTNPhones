package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.projection.CallView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backoffice")
public class CallsBackOficeController {

    private final CallController callController;

    @Autowired
    public CallsBackOficeController(CallController callController) {
        this.callController = callController;
    }

    @GetMapping("/clients/{dni}/calls")
    public ResponseEntity<List<CallView>> getCallsByDni(@PathVariable(value = "dni") String dni) throws ValidationException, UserNotFoundException {

        List<CallView> calls = callController.getCallsByDni(dni);
        return (calls.size() != 0) ? ResponseEntity.ok(calls) : ResponseEntity.noContent().build();
    }

}
