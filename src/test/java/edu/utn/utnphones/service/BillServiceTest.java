package edu.utn.utnphones.service;

import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.repository.BillDao;
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

public class BillServiceTest {

    BillService billService;
    Bill bill;
    List<Bill> bills;

    //Dates
    String sDate;
    String sExpirationDate;
    Date date;
    Date expirationDate;

    @Mock
    BillDao billDao;
    @Mock
    UserService userService;

    @Before
    public void setUp() throws ParseException {

        initMocks(this);
        billService = new BillService(billDao, userService);

        sDate = "01/02/2003";
        sExpirationDate = "09/12/2018";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        expirationDate = new SimpleDateFormat("dd/MM/yyyy").parse(sExpirationDate);

        bill = new Bill((long)1, 25, 123.5, 250.75, LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()), false, null);

        bills = new ArrayList<>();
        bills.add(bill);
    }

    @Test
    public void testGetBillsByUserIdOk(){

        when(billDao.getBillsByUserId((long)1)).thenReturn(bills);

        List<Bill> returnedBills = billService.getBillsByUserId((long)1);
        assertEquals(bills, returnedBills);
        verify(billDao, times(1)).getBillsByUserId((long)1);
    }

    @Test
    public void testGetBillsByUserLoggedOk(){

        when(billDao.getBillsByUserLogged((long)1, LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault()), LocalDateTime.ofInstant(expirationDate.toInstant(),
                ZoneId.systemDefault()))).thenReturn(bills);
        List<Bill> returnedBills = billService.getBillsByUserLogged((long)1, date , expirationDate);
        assertEquals(bills, returnedBills);
        verify(billDao, times(1)).getBillsByUserLogged((long)1, LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault()), LocalDateTime.ofInstant(expirationDate.toInstant(),
                ZoneId.systemDefault()));
    }

    @Test
    public void testGetBillsByUserDniOk() throws UserNotFoundException {

        when(billDao.getBillsByUserDni("1234")).thenReturn(bills);
        List<Bill> returnedBill = billService.getBillsByUserDni("1234");
        assertEquals(bills, returnedBill);
        verify(billDao, times(1)).getBillsByUserDni("1234");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetBillsByUserDniUserNotFound() throws UserNotFoundException {

        when(userService.getClientByDni("123456")).thenThrow(UserNotFoundException.class);
        billService.getBillsByUserDni("123456");
    }
}