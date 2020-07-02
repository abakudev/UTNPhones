package edu.utn.utnphones.controller.webApp;


import edu.utn.utnphones.controller.BillController;
import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Province;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.enums.Role;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.projection.CallView;
import edu.utn.utnphones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserWebControllerTest {

    UserWebController userWebController;

    Bill bill;
    List<Bill> billList;
    //Dates
    String sDate;
    String sExpirationDate;
    Date date;
    Date expirationDate;

    CallView callView;
    List<CallView> callViewList;

    City city;
    List<City> cityList;

    User user;

    @Mock
    CallController callController;
    @Mock
    BillController billController;
    @Mock
    SessionManager sessionManager;



    @Before
    public void setUp() throws ParseException {
        initMocks(this);
        userWebController = new UserWebController(callController, billController, sessionManager);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        callView = factory.createProjection(CallView.class);

        sDate = "01/02/2003";
        sExpirationDate = "09/12/2018";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(sExpirationDate);

        callView.setDni("32165498");
        callView.setPhoneNumberOrigin("022365899633");
        callView.setPhoneNumberDestination("02203658896");
        callView.setCityOrigin("Mar del Plata");
        callView.setCityDestination("Merlo");
        callView.setDuration(125);
        callView.setDate(date);

        callViewList = new ArrayList<>();
        callViewList.add(callView);

        city = new City((long) 3,"Mar del Plata", "0223", new Province((long)1 ,"Buenos Aires"));
        cityList = new ArrayList<>();
        cityList.add(city);

        bill = new Bill((long)1, 25, 123.5, 250.75, LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()), false, null);
        billList = new ArrayList<>();
        billList.add(bill);

        user = new User((long)1, "32165498","Juan","Perez","12354",true, Role.client,null );
    }


    // GetBills
    @Test
    public void testGetBillsOk() throws UserNotFoundException, ParseException {

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(billController.getBillsByUserLogged((long)1, date, expirationDate)).thenReturn(billList);

        ResponseEntity<List<Bill>> response = userWebController.getBills("321654asdasd", sDate, sExpirationDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(billList, response.getBody());
        verify(billController, times(1)).getBillsByUserLogged((long)1, date, expirationDate);
    }

    @Test
    public void testGetBillsNoContent() throws UserNotFoundException, ParseException {

        List<Bill> listEmpty = new ArrayList<>();

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(billController.getBillsByUserLogged((long)1, date, expirationDate)).thenReturn(listEmpty);

        ResponseEntity<List<Bill>> response = userWebController.getBills("321654asdasd", sDate, sExpirationDate);

        assertEquals(204, response.getStatusCodeValue());
        verify(billController, times(1)).getBillsByUserLogged((long)1, date, expirationDate);
    }

    @Test
    public void testGetBillsDateNull() throws UserNotFoundException, ParseException {

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(billController.getBillsByUserId((long)1)).thenReturn(billList);

        ResponseEntity<List<Bill>> response = userWebController.getBills("321654asdasd", null, sExpirationDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(billList, response.getBody());
        verify(billController, times(1)).getBillsByUserId((long)1);
    }

    @Test
    public void testGetBillsDateNull2() throws UserNotFoundException, ParseException {

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(billController.getBillsByUserId((long)1)).thenReturn(billList);

        ResponseEntity<List<Bill>> response = userWebController.getBills("321654asdasd", sExpirationDate, null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(billList, response.getBody());
        verify(billController, times(1)).getBillsByUserId((long)1);
    }

    //GetCalls
    @Test
    public void testGetCallsOk() throws UserNotFoundException, ParseException, ValidationException {

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(callController.getCallsByUserFilterByDate(user.getDni(), date, expirationDate)).thenReturn(callViewList);

        ResponseEntity<List<CallView>> response = userWebController.getCalls("321654asdasd", sDate, sExpirationDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(callViewList, response.getBody());
        verify(callController, times(1)).getCallsByUserFilterByDate(user.getDni(), date, expirationDate);
    }

    @Test
    public void testGetCallsNoContent() throws UserNotFoundException, ParseException, ValidationException {

        List<CallView> listEmpty = new ArrayList<>();

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(callController.getCallsByUserFilterByDate(user.getDni(), date, expirationDate)).thenReturn(listEmpty);

        ResponseEntity<List<CallView>> response = userWebController.getCalls("321654asdasd", sDate, sExpirationDate);

        assertEquals(204, response.getStatusCodeValue());
        verify(callController, times(1)).getCallsByUserFilterByDate(user.getDni(), date, expirationDate);
    }

    @Test
    public void testGetCallsDateNull() throws UserNotFoundException, ParseException, ValidationException {

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(callController.getCallsByDni(user.getDni())).thenReturn(callViewList);

        ResponseEntity<List<CallView>> response = userWebController.getCalls("321654asdasd", null, sExpirationDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(callViewList, response.getBody());
        verify(callController, times(1)).getCallsByDni(user.getDni());
    }

    @Test
    public void testGetCallsDateNull2() throws UserNotFoundException, ParseException, ValidationException {

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(callController.getCallsByDni(user.getDni())).thenReturn(callViewList);

        ResponseEntity<List<CallView>> response = userWebController.getCalls("321654asdasd", sExpirationDate, null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(callViewList, response.getBody());
        verify(callController, times(1)).getCallsByDni(user.getDni());
    }

    //GetTOP10MostCalledDestination
    @Test
    public void testGetTOP10MostCalledDestinationOk() throws UserNotFoundException, ValidationException {

        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(callController.getTOP10MostCalledDestination(user.getUserId())).thenReturn(cityList);

        ResponseEntity<List<City>> response = userWebController.getTOP10MostCalledDestination("321654asdasd");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cityList, response.getBody());
        verify(callController, times(1)).getTOP10MostCalledDestination(user.getUserId());
    }

    @Test
    public void testGetTOP10MostCalledDestinationNoContent() throws UserNotFoundException {

        List<City> listEmpty = new ArrayList<>();
        when(sessionManager.getCurrentUser("321654asdasd")).thenReturn(user);
        when(callController.getTOP10MostCalledDestination(user.getUserId())).thenReturn(listEmpty);

        ResponseEntity<List<City>> response = userWebController.getTOP10MostCalledDestination("321654asdasd");

        assertEquals(204, response.getStatusCodeValue());
        verify(callController, times(1)).getTOP10MostCalledDestination(user.getUserId());
    }


}