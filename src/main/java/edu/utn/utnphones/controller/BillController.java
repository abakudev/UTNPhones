package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    public List<Bill>getBillsByUserId(Long id) {
        return billService.getBillsByUserId(id);
    }

    public List<Bill> getBillsByUserDni(String dni) throws UserNotFoundException, ValidationException {
        if((dni != null) &&(!dni.isEmpty())){
            return billService.getBillsByUserDni(dni);
        }else{
            throw new ValidationException("dni must have a value");
        }
    }

    public List<Bill> getBillsByUserLogged(Long id, Date from , Date to) {
        return billService.getBillsByUserLogged(id, from, to);
    }
}
