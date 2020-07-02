package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.dto.CallRequestDto;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.projection.CallView;
import edu.utn.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
public class CallController {

    private final CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    public Call addCall(CallRequestDto call) throws ValidationException, ParseException {
        if(!call.isValid()) throw new ValidationException("Error - does not include all necessary information ");
        return callService.addCall(call);
    }

    public List<CallView> getCallsByDni(String dni) throws ValidationException, UserNotFoundException {

        if( (dni != null) &&(!dni.isEmpty()) ){
            return callService.getCallsByDni(dni);
        }else{
            throw new ValidationException("dni must have a value");
        }
    }

    public List<CallView> getCallsByUserFilterByDate(String dni, Date from, Date to) throws ValidationException, UserNotFoundException {

        if( (dni != null) &&(!dni.isEmpty()) ){
            return callService.getCallsByUserFilterByDate(dni, from, to);
        }else{
            throw new ValidationException("dni must have a value");
        }
    }

    public List<City> getTOP10MostCalledDestination(Long id) {
        return callService.getTOP10MostCalledDestination(id);
    }

}
