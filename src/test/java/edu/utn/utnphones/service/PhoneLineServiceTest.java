package edu.utn.utnphones.service;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.enums.Role;
import edu.utn.utnphones.domain.enums.TypeLine;
import edu.utn.utnphones.dto.PhoneLineRequestDto;
import edu.utn.utnphones.exceptions.PhoneLineNotFoundException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.repository.PhoneLineDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineServiceTest {

    PhoneLineService phoneLineService;

    PhoneLineRequestDto phoneLineDto;
    PhoneLine phoneLine;
    List<PhoneLine> phoneLines;

    @Mock
    PhoneLineDao phoneLineDao;
    @Mock
    UserService userService;

    @Before
    public void setUp(){

        initMocks(this);
        phoneLineService = new PhoneLineService(phoneLineDao, userService);

        phoneLine = new PhoneLine((long)12, "5699856", TypeLine.mobile, true, null);
        phoneLines = new ArrayList<>();
        phoneLines.add(phoneLine);
    }


    @Test
    public void testAddPhoneLineOk() throws UserNotFoundException {

        User user = new User((long)1, "32165498","Juan","Perez","12354",true, Role.client,null );
        Long id = (long)123;
        PhoneLine phoneLine = new PhoneLine((long)123, "1234", TypeLine.mobile , true, user);
        phoneLineDto = new PhoneLineRequestDto("32165498", TypeLine.mobile);

        when(userService.getClientByDni("32165498")).thenReturn(user);

        when(phoneLineDao.generateNumber("32165498", "mobile" )).thenReturn(id);
        when(phoneLineDao.getOne(id)).thenReturn(phoneLine);

        PhoneLine phoneLineReturned = phoneLineService.addPhoneLine(phoneLineDto);
        assertEquals(phoneLine, phoneLineReturned);

        verify(phoneLineDao, times(1)).generateNumber("32165498", "mobile" );
    }

    @Test(expected = UserNotFoundException.class)
    public void testAddPhoneLineUserNotFound() throws UserNotFoundException {

        phoneLineDto = new PhoneLineRequestDto("32165", TypeLine.mobile);
        when(userService.getClientByDni("32165")).thenThrow(UserNotFoundException.class);
        phoneLineService.addPhoneLine(phoneLineDto);
    }

    @Test
    public void testgetPhoneLinesByUserDniOk() throws UserNotFoundException {

        User user = new User((long)1, "32165498","Juan","Perez","12354",true, Role.client,null );
        when(userService.getClientByDni("1234")).thenReturn(user);
        when(phoneLineDao.getPhoneLinesByDni("1234")).thenReturn(phoneLines);

        List<PhoneLine> returndedPhoneLines = phoneLineService.getPhoneLinesByUserDni("1234");

        assertEquals(phoneLines, returndedPhoneLines);

        verify(phoneLineDao, times(1)).getPhoneLinesByDni("1234");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetPhoneLinesByUserDniUserNotFound() throws UserNotFoundException {
        when(userService.getClientByDni("32165498")).thenThrow(UserNotFoundException.class);
        phoneLineService.getPhoneLinesByUserDni("32165498");
    }

    @Test
    public void testGetPhoneLinesOk(){

        when(phoneLineDao.getPhoneLines()).thenReturn(phoneLines);
        List<PhoneLine> returnedPhoneLines = phoneLineService.getPhoneLines();
        assertEquals(phoneLines, returnedPhoneLines);
    }

    @Test
    public void testDeletePhoneLineOk() throws PhoneLineNotFoundException {

        phoneLine = new PhoneLine((long)14, "5677362", TypeLine.home, true, null);
        when(phoneLineDao.getOne((long)14)).thenReturn(phoneLine);
        doNothing().when(phoneLineDao).suspendPhoneLine((long)14);
        phoneLineService.deletePhoneLine((long)14);
        verify(phoneLineDao,times(1)).suspendPhoneLine((long)14);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testDeletePhoneLinePhoneLineNotFoundException() throws PhoneLineNotFoundException {

        when(phoneLineDao.getOne((long)12)).thenThrow(PhoneLineNotFoundException.class);
        phoneLineService.deletePhoneLine((long)12);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testDeletePhoneLineEnabledFalse() throws PhoneLineNotFoundException {

        phoneLine.setEnabled(false);
        when(phoneLineDao.getOne((long)14)).thenReturn(phoneLine);
        phoneLineService.deletePhoneLine( (long)14 );
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testDeletePhoneLineNull() throws PhoneLineNotFoundException {

        phoneLine = null;
        when(phoneLineDao.getOne((long)14)).thenReturn(phoneLine);
        phoneLineService.deletePhoneLine( (long)14 );
    }

    @Test
    public void testUpdatePhoneLineOk() throws PhoneLineNotFoundException {

        phoneLine = new PhoneLine((long)15, "5677362", TypeLine.home, false, null);

        when(phoneLineDao.findById((long)15)).thenReturn(Optional.ofNullable(phoneLine));
        when(phoneLineDao.activePhoneLine((long)15)).thenReturn((long)15);
        when(phoneLineDao.getOne((long)15)).thenReturn(phoneLine);

        PhoneLine returnedPhoneLine = phoneLineService.updatePhoneLine((long)15);

        assertEquals(phoneLine, returnedPhoneLine);
        verify(phoneLineDao,times(1)).activePhoneLine((long)15);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testUpdatePhoneLineLineNotFoundException() throws PhoneLineNotFoundException {

        when(phoneLineDao.getOne((long)12)).thenThrow(PhoneLineNotFoundException.class);
        phoneLineService.updatePhoneLine((long)12);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testUpdatePhoneLineLineEnabledFalse() throws PhoneLineNotFoundException {

        phoneLine.setEnabled(true);
        when(phoneLineDao.getOne((long)14)).thenReturn(phoneLine);
        phoneLineService.updatePhoneLine( (long)14 );
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testUpdatePhoneLineLineNull() throws PhoneLineNotFoundException {

        phoneLine = null;
        when(phoneLineDao.getOne((long)14)).thenReturn(phoneLine);
        phoneLineService.updatePhoneLine( (long)14 );
    }







}