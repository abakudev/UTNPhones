package edu.utn.utnphones.controller.webApp;

import edu.utn.utnphones.controller.BillController;
import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.projection.CallView;
import edu.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/web")
public class UserWebController {

    private final CallController callController;
    private final BillController billController;
    private final SessionManager sessionManager;

    @Autowired
    public UserWebController(CallController callController, BillController billController, SessionManager sessionManager) {
        this.callController = callController;
        this.billController = billController;
        this.sessionManager = sessionManager;
    }


    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> getBills(@RequestHeader("Authorization") String sessionToken, String from, String to) throws ParseException, UserNotFoundException {
        User currentUser = getCurrentUser(sessionToken);
        List<Bill> bills ;
        if ((from != null) && (to != null)) {
            Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(from);
            Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(to);
            bills = billController.getBillsByUserLogged(currentUser.getUserId(), fromDate, toDate);
        } else {
            bills = billController.getBillsByUserId(currentUser.getUserId());
        }
        return (bills.size() > 0) ? ResponseEntity.ok(bills) : ResponseEntity.noContent().build();
    }


    @GetMapping("/calls")
    public ResponseEntity<List<CallView>> getCalls(@RequestHeader("Authorization") String sessionToken, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) throws ParseException, ValidationException, UserNotFoundException {
        User currentUser = getCurrentUser(sessionToken);
        List<CallView> calls;
        if ((from != null) && (to != null)) {
            Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(from);
            Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(to);
            calls = callController.getCallsByUserFilterByDate(currentUser.getDni(), fromDate, toDate);
        } else {
            calls = callController.getCallsByDni(currentUser.getDni());
        }
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.noContent().build();
    }

    @GetMapping("cities/top-10/calls")
    public ResponseEntity<List<City>> getTOP10MostCalledDestination (@RequestHeader("Authorization") String sessionToken) throws UserNotFoundException {
        List<City> callsDestination = callController.getTOP10MostCalledDestination(getCurrentUser(sessionToken).getUserId());
        return (callsDestination.size() > 0) ? ResponseEntity.ok(callsDestination) : ResponseEntity.noContent().build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotFoundException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotFoundException::new);
    }
}
