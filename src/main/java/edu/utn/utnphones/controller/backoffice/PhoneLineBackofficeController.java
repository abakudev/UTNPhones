package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.PhoneLineRequestDto;
import edu.utn.utnphones.exceptions.PhoneLineNotFoundException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.utils.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backoffice")
public class PhoneLineBackofficeController {

    private final PhoneLineController phoneLineController;

    @Autowired
    public PhoneLineBackofficeController(PhoneLineController phoneLineController) {
        this.phoneLineController = phoneLineController;
    }

    @PostMapping("/phone-lines")
    public ResponseEntity newPhoneLine(@RequestBody PhoneLineRequestDto newPhoneLine) throws ValidationException, UserNotFoundException {

        PhoneLine phoneLine = phoneLineController.addPhoneLine(newPhoneLine);
        return ResponseEntity.created(UriUtils.getLocation(phoneLine.getPhoneLineId())).build();
    }

    @GetMapping("clients/{dni}/phone-lines")
    public ResponseEntity<List<PhoneLine>> getPhoneLinesByDni(@PathVariable(value = "dni") String dni) throws ValidationException, UserNotFoundException {

        List<PhoneLine> phoneLines = phoneLineController.getPhoneLinesByUserDni(dni);
        return (phoneLines.size() != 0) ? ResponseEntity.ok(phoneLines) : ResponseEntity.noContent().build();
    }

    @GetMapping("phone-lines")
    public ResponseEntity<List<PhoneLine>> getPhoneLines(){

        List<PhoneLine> phoneLines = phoneLineController.getPhoneLines();
        return (phoneLines.size() > 0) ? ResponseEntity.ok(phoneLines) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("phone-lines/{id}")
    public ResponseEntity suspendPhone(@PathVariable("id")Long idPhoneLine) throws PhoneLineNotFoundException, ValidationException {

        phoneLineController.deletePhoneLine(idPhoneLine);
        return ResponseEntity.ok().build();
    }

    @PutMapping("phone-lines/{id}")
    public ResponseEntity<PhoneLine> updatePhoneLine(@PathVariable("id")Long idPhoneLine) throws PhoneLineNotFoundException, ValidationException {

        PhoneLine phoneLine = phoneLineController.updatePhoneLine(idPhoneLine);
        return ResponseEntity.accepted().body(phoneLine);
    }

}
