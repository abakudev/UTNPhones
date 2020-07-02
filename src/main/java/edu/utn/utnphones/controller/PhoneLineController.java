package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.PhoneLineRequestDto;
import edu.utn.utnphones.exceptions.PhoneLineNotFoundException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PhoneLineController {

    private final PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    // Create PhoneLine By User Id
    public PhoneLine addPhoneLine (PhoneLineRequestDto newPhoneLine) throws ValidationException, UserNotFoundException {

        if((newPhoneLine.getDni() != null)&&(!newPhoneLine.getDni().isEmpty()) && (newPhoneLine.getTypeLine() != null) ){
            return phoneLineService.addPhoneLine(newPhoneLine);
        }else{
            throw new ValidationException(" dni and typeLine must have a value ");
        }
    }

    // Get PhoneLines by User Dni
    public List<PhoneLine> getPhoneLinesByUserDni(String dni) throws ValidationException, UserNotFoundException {
        if ((dni != null) &&(!dni.isEmpty())){
            return phoneLineService.getPhoneLinesByUserDni(dni);
        }else {
            throw new ValidationException("dni must have a value");
        }
    }

    public List<PhoneLine> getPhoneLines() {
        return phoneLineService.getPhoneLines();
    }

    // Delete PhoneLine
    public void deletePhoneLine(Long idPhone) throws PhoneLineNotFoundException, ValidationException {

        if(idPhone!=null){
            phoneLineService.deletePhoneLine(idPhone);
        }else{
            throw new ValidationException("idPhone cannot be null");
        }
    }

    // Update PhoneLine By User Dni
    public PhoneLine updatePhoneLine(Long idPhone) throws PhoneLineNotFoundException, ValidationException {

        if(idPhone!=null){
            return phoneLineService.updatePhoneLine(idPhone);
        }else{
            throw new ValidationException("idPhone cannot be null");
        }
    }

}
