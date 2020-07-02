package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.service.BillService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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

public class BillControllerTest {

    BillController billController;
    Bill bill;
    List<Bill> bills;
    //Dates
    String sDate;
    String sExpirationDate;
    Date date;
    Date expirationDate;

    @Mock
    BillService billService;

    @Before
    public void setUp() throws ParseException {

        initMocks(this);
        billController = new BillController(billService);

        sDate = "01/02/2003";
        sExpirationDate = "09/12/2018";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(sExpirationDate);

        bill = new Bill((long)1, 25, 123.5, 250.75, LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()), false, null);

        bills = new ArrayList<>();
        bills.add(bill);
    }


    //GetBillsByUserId
    @Test
    public void testGetBillsByUserIdOk(){

        when(billService.getBillsByUserId((long)987)).thenReturn(bills);
        List<Bill> returnedBills = billController.getBillsByUserId((long)987);
        assertEquals(bills, returnedBills);
        verify(billService, times(1)).getBillsByUserId((long)987);
    }

    //GetBillsByUserDni
    @Test
    public void testGetBillsByUserDniOk() throws UserNotFoundException, ValidationException {

        when(billService.getBillsByUserDni("321654987")).thenReturn(bills);
        List<Bill> returnedBills = billController.getBillsByUserDni("321654987");
        assertEquals(bills, returnedBills);
        verify(billService, times(1)).getBillsByUserDni("321654987");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetBillsByUserDniUserNotFoundException() throws UserNotFoundException, ValidationException {

        when(billService.getBillsByUserDni("321654")).thenThrow(UserNotFoundException.class);
        billController.getBillsByUserDni("321654");

    }

    @Test(expected = ValidationException.class)
    public void testGetBillsByUserDniInvalidData() throws UserNotFoundException, ValidationException {

        billController.getBillsByUserDni("");
    }

    @Test(expected = ValidationException.class)
    public void testGetBillsByUserDniNull() throws UserNotFoundException, ValidationException {

        billController.getBillsByUserDni(null);
    }

    //GetBillsByUserLogged
    @Test
    public void testGetBillsByUserLoggedOk(){

        when(billService.getBillsByUserLogged((long)123, date, expirationDate)).thenReturn(bills);
        List<Bill> returnedBills = billController.getBillsByUserLogged((long)123, date, expirationDate);
        assertEquals(bills, returnedBills);
        verify(billService, times(1)).getBillsByUserLogged((long)123, date, expirationDate);
    }






}