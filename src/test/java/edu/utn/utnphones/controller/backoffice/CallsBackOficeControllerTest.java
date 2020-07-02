package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.projection.CallView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallsBackOficeControllerTest {

    CallsBackOficeController callsBackOficeController;
    CallView callView;

    @Mock
    CallController callController;

    @Before
    public void setUp(){
        initMocks(this);
        callsBackOficeController = new CallsBackOficeController(callController);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        callView = factory.createProjection(CallView.class);
    }

    @Test
    public void testGetCallsByDniOk() throws ValidationException, UserNotFoundException {
        callView.setDni("32165498");
        callView.setPhoneNumberOrigin("022365899633");
        callView.setPhoneNumberDestination("02203658896");
        callView.setCityOrigin("Mar del Plata");
        callView.setCityDestination("Merlo");
        callView.setDuration(125);
        callView.setDate(new Date());

        List<CallView> callViewList = new ArrayList<>();
        callViewList.add(callView);
        when(callController.getCallsByDni(callView.getDni())).thenReturn(callViewList);
        ResponseEntity<List<CallView>> response = callsBackOficeController.getCallsByDni(callView.getDni());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(callViewList, response.getBody());
    }

    @Test
    public void testGetCallsByDniNoContent() throws ValidationException, UserNotFoundException {

        List<CallView> callViewList = new ArrayList<>();
        when(callController.getCallsByDni(callView.getDni())).thenReturn(callViewList);
        ResponseEntity<List<CallView>> response = callsBackOficeController.getCallsByDni(callView.getDni());
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetCallsByDniUserNotExist() throws ValidationException, UserNotFoundException {

        when(callController.getCallsByDni("3216954")).thenThrow(UserNotFoundException.class);
        callsBackOficeController.getCallsByDni("3216954");
    }

    @Test(expected = ValidationException.class)
    public void testGetCallsByDniEmpty() throws ValidationException, UserNotFoundException {

        when(callController.getCallsByDni("")).thenThrow(ValidationException.class);
        callsBackOficeController.getCallsByDni("");
    }

}