package edu.utn.utnphones.controller.infrastructure;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.dto.CallRequestDto;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.utils.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class InfraController {

    private final CallController callController;

    @Autowired
    public InfraController(CallController callController) {
        this.callController = callController;
    }

    @PostMapping("/calls")
    public ResponseEntity addCall(@RequestBody CallRequestDto callDto) throws ValidationException, ParseException {

        Call call = callController.addCall(callDto);
        return ResponseEntity.created(UriUtils.getLocation(call.getCallId())).build();
    }



}
