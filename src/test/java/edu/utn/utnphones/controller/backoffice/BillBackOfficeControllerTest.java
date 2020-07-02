package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.BillController;
import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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

public class BillBackOfficeControllerTest {

    BillBackOfficeController billBackOfficeController;
    Bill bill;
    List<Bill> bills;
    //Dates
    String sDate;
    String sExpirationDate;
    Date date;
    Date expirationDate;

    @Mock
    BillController billController;

    @Before
    public void setUp() throws ParseException {

        initMocks(this);
        billBackOfficeController = new BillBackOfficeController(billController);

        sDate = "01/02/2003";
        sExpirationDate = "09/12/2018";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(sExpirationDate);

        bill = new Bill((long)1, 25, 123.5, 250.75, LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()), false, null);

        bills = new ArrayList<>();
        bills.add(bill);
    }

    @Test
    public void testGetBillsByUserOk() throws UserNotFoundException, ValidationException {

        when(billController.getBillsByUserDni("12345")).thenReturn(bills);
        ResponseEntity<List<Bill>> response = billBackOfficeController.getBillsByUser("12345");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bills, response.getBody());
        verify(billController, times(1)).getBillsByUserDni("12345");
    }

    @Test
    public void testGetBillsByUserNoContent() throws UserNotFoundException, ValidationException {

        List<Bill> emptyList = new ArrayList<>();
        when(billController.getBillsByUserDni("12345")).thenReturn(emptyList);
        ResponseEntity<List<Bill>> response = billBackOfficeController.getBillsByUser("12345");
        assertEquals(204, response.getStatusCodeValue());
        verify(billController, times(1)).getBillsByUserDni("12345");
    }

    @Test(expected = ValidationException.class)
    public void testGetBillsByUserInvalidData() throws UserNotFoundException, ValidationException {

        when(billController.getBillsByUserDni("")).thenThrow(ValidationException.class);
        billBackOfficeController.getBillsByUser("");
    }

    @Test(expected = ValidationException.class)
    public void testGetBillsByUserNull() throws UserNotFoundException, ValidationException {

        when(billController.getBillsByUserDni(null)).thenThrow(ValidationException.class);
        billBackOfficeController.getBillsByUser(null);
    }

}