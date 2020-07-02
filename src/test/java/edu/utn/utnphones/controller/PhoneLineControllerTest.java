package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.enums.TypeLine;
import edu.utn.utnphones.dto.PhoneLineRequestDto;
import edu.utn.utnphones.exceptions.PhoneLineNotFoundException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.service.PhoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineControllerTest {

    PhoneLineController phoneLineController;

    PhoneLine phoneLine;
    List<PhoneLine> phoneLines;
    PhoneLineRequestDto phoneLineDto;

    @Mock
    PhoneLineService phoneLineService;

    @Before
    public void setUp(){

        initMocks(this);
        phoneLineController = new PhoneLineController(phoneLineService);

        phoneLine = new PhoneLine((long)77, "5668963", TypeLine.home, true, null);
        phoneLines = new ArrayList<>();
        phoneLines.add(phoneLine);
    }

    //add
    @Test
    public void testAddPhoneLineOk() throws UserNotFoundException, ValidationException {

        phoneLineDto = new PhoneLineRequestDto("32165498", TypeLine.home);
        when(phoneLineService.addPhoneLine(phoneLineDto)).thenReturn(phoneLine);
        PhoneLine returnedPhoneLine = phoneLineController.addPhoneLine(phoneLineDto);
        assertEquals(phoneLine, returnedPhoneLine);
        verify(phoneLineService, times(1)).addPhoneLine(phoneLineDto);
    }

    @Test(expected = ValidationException.class)
    public void testAddPhoneLineDniEmpty() throws ValidationException, UserNotFoundException {
        phoneLineDto = new PhoneLineRequestDto("", TypeLine.home);
        phoneLineController.addPhoneLine(phoneLineDto);
    }

    @Test(expected = ValidationException.class)
    public void testAddPhoneLineDniNull() throws ValidationException, UserNotFoundException {
        phoneLineDto = new PhoneLineRequestDto(null, TypeLine.home);
        phoneLineController.addPhoneLine(phoneLineDto);
    }

    @Test(expected = ValidationException.class)
    public void testAddPhoneLineNull() throws ValidationException, UserNotFoundException {

        phoneLineDto = new PhoneLineRequestDto(null,null );
        phoneLineController.addPhoneLine(phoneLineDto);
    }

    @Test(expected = ValidationException.class)
    public void testAddPhoneLineTypeNull() throws ValidationException, UserNotFoundException {
        phoneLineDto = new PhoneLineRequestDto("32165498", null);
        phoneLineController.addPhoneLine(phoneLineDto);
    }

    //GetPhoneLinesByUserDni
    @Test
    public void testGetPhoneLinesByUserDniOk() throws UserNotFoundException, ValidationException {

        when(phoneLineService.getPhoneLinesByUserDni("32165498")).thenReturn(phoneLines);
        List<PhoneLine> returnedPhoneLines = phoneLineController.getPhoneLinesByUserDni("32165498");
        assertEquals(phoneLines, returnedPhoneLines);
        verify(phoneLineService, times(1)).getPhoneLinesByUserDni("32165498");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetPhoneLinesByUserDniUserNotFound() throws UserNotFoundException, ValidationException {
        when(phoneLineService.getPhoneLinesByUserDni("1234")).thenThrow(UserNotFoundException.class);
        phoneLineController.getPhoneLinesByUserDni("1234");
    }

    @Test(expected = ValidationException.class)
    public void testGetPhoneLinesByUserDniInvalidData() throws ValidationException, UserNotFoundException {
        phoneLineController.getPhoneLinesByUserDni("");
    }

    @Test(expected = ValidationException.class)
    public void testGetPhoneLinesByUserDniDniNull() throws UserNotFoundException, ValidationException {
        phoneLineController.getPhoneLinesByUserDni(null);
    }

    //GetPhoneLines
    @Test
    public void testGetPhoneLinesOk(){

        when(phoneLineService.getPhoneLines()).thenReturn(phoneLines);
        List<PhoneLine> returnedPhoneLines = phoneLineController.getPhoneLines();
        assertEquals(phoneLines, returnedPhoneLines);
        verify(phoneLineService, times(1)).getPhoneLines();
    }

    //DeletePhoneLine
    @Test
    public void testDeletePhoneLineOk() throws PhoneLineNotFoundException, ValidationException {

        doNothing().when(phoneLineService).deletePhoneLine((long)654);
        phoneLineController.deletePhoneLine((long)654);
        verify(phoneLineService, times(1)).deletePhoneLine((long)654);
    }

    @Test(expected = ValidationException.class)
    public void testDeletePhoneLineNull() throws PhoneLineNotFoundException, ValidationException {

        doNothing().when(phoneLineService).deletePhoneLine(null);
        phoneLineController.deletePhoneLine(null);
        verify(phoneLineService, times(1)).deletePhoneLine(null);
    }

    //UpdatePhoneLine
    @Test
    public void testUpdatePhoneLineOk() throws PhoneLineNotFoundException, ValidationException {

        when(phoneLineService.updatePhoneLine((long)654)).thenReturn(phoneLine);
        PhoneLine returnedPhoneLine = phoneLineController.updatePhoneLine((long)654);
        assertEquals(phoneLine, returnedPhoneLine);
        verify(phoneLineService, times(1)).updatePhoneLine((long)654);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testUpdatePhoneLineNotFoundException() throws PhoneLineNotFoundException, ValidationException {

        when(phoneLineService.updatePhoneLine((long)987)).thenThrow(PhoneLineNotFoundException.class);
        phoneLineController.updatePhoneLine((long)987);
    }

    @Test(expected = ValidationException.class)
    public void testUpdatePhoneLineNull() throws PhoneLineNotFoundException, ValidationException {

        when(phoneLineService.updatePhoneLine(null)).thenThrow(ValidationException.class);
        phoneLineController.updatePhoneLine((null));
    }


}