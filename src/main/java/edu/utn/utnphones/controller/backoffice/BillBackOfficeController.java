package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.BillController;
import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backoffice")
public class BillBackOfficeController {

    private final BillController billController;

    @Autowired
    public BillBackOfficeController(BillController billController) {
        this.billController = billController;
    }

    @GetMapping("/clients/{dni}/bills")
    public ResponseEntity<List<Bill>> getBillsByUser(@PathVariable("dni") String dni) throws UserNotFoundException, ValidationException {
        List<Bill> bills = billController.getBillsByUserDni(dni);
        return (bills.size() != 0) ? ResponseEntity.ok(bills) : ResponseEntity.noContent().build();
    }

}
