package edu.utn.utnphones.controller.backoffice;


import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.enums.TypeLine;
import edu.utn.utnphones.dto.PhoneLineRequestDto;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineBackofficeControllerTest {

    PhoneLineBackofficeController phoneLineBackController;

    PhoneLine phoneLine;
    List<PhoneLine> phoneLines;
    PhoneLineRequestDto phoneLineDto;

    @Mock
    PhoneLineController phoneLineController;

    @Before
    public void setUp(){

        initMocks(this);
        phoneLineBackController = new PhoneLineBackofficeController(phoneLineController);

        phoneLine = new PhoneLine((long)77, "5668963", TypeLine.home, true, null);
        phoneLines = new ArrayList<>();
        phoneLines.add(phoneLine);
    }


//    @Test
//    public void testNewPhoneLineOk() throws ValidationException, UserNotFoundException {
//
//        phoneLineDto = new PhoneLineRequestDto("32165498", TypeLine.home);
//        when(UriUtils.getLocation(phoneLine.getPhoneLineId())).thenReturn(URI.create("uri.net"));
//        when(phoneLineController.addPhoneLine(phoneLineDto)).thenReturn(phoneLine);
//        ResponseEntity response = phoneLineBackController.newPhoneLine(phoneLineDto);
//        assertEquals(URI.create("URI/test"), response.getHeaders().getLocation() );
//        assertEquals(201, response.getStatusCodeValue());
//    }


    @Test
    public void testGetPhoneLinesByDniOk() throws ValidationException, UserNotFoundException {

        when(phoneLineController.getPhoneLinesByUserDni("12345")).thenReturn(phoneLines);
        ResponseEntity<List<PhoneLine>> response = phoneLineBackController.getPhoneLinesByDni("12345");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(phoneLines, response.getBody());
        verify(phoneLineController, times(1)).getPhoneLinesByUserDni("12345");
    }

    @Test
    public void testGetPhoneLinesByDniNoContent() throws ValidationException, UserNotFoundException {

        List<PhoneLine> listEmpty = new ArrayList<>();
        when(phoneLineController.getPhoneLinesByUserDni("12345")).thenReturn(listEmpty);
        ResponseEntity<List<PhoneLine>> response = phoneLineBackController.getPhoneLinesByDni("12345");
        assertEquals(204, response.getStatusCodeValue());
        verify(phoneLineController, times(1)).getPhoneLinesByUserDni("12345");
    }

    @Test(expected = ValidationException.class)
    public void testGetPhoneLinesByDniInvalidData() throws ValidationException, UserNotFoundException {
        when(phoneLineController.getPhoneLinesByUserDni("")).thenThrow(ValidationException.class);
        phoneLineBackController.getPhoneLinesByDni("");
    }

    @Test(expected = ValidationException.class)
    public void testGetPhoneLinesByDniNull() throws ValidationException, UserNotFoundException {
        when(phoneLineController.getPhoneLinesByUserDni(null)).thenThrow(ValidationException.class);
        phoneLineBackController.getPhoneLinesByDni(null);
    }

    @Test
    public void testGetPhoneLinesOk(){

        when(phoneLineController.getPhoneLines()).thenReturn(phoneLines);
        ResponseEntity<List<PhoneLine>> response = phoneLineBackController.getPhoneLines();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(phoneLines, response.getBody());
        verify(phoneLineController, times(1)).getPhoneLines();
    }

    @Test
    public void testGetPhoneLinesNoContent(){

        List<PhoneLine> listEmpty = new ArrayList<>();
        when(phoneLineController.getPhoneLines()).thenReturn(listEmpty);
        ResponseEntity<List<PhoneLine>> response = phoneLineBackController.getPhoneLines();
        assertEquals(204, response.getStatusCodeValue());
        verify(phoneLineController, times(1)).getPhoneLines();
    }

/*
    //SuspendPhone
    @Test
    public void testSuspendPhoneOk() throws PhoneLineNotFoundException {


        when(phoneLineController.deletePhoneLine((phoneLine.getPhoneLineId()))).thenReturn(new PhoneLine());
        ResponseEntity response = phoneLineBackController.suspendPhone((long)123);
        assertEquals(200, response.getStatusCodeValue());




        //when(phoneLineController.deletePhoneLine((long)123)).thenReturn()


    }*/





    //UpdatePhoneLine




}