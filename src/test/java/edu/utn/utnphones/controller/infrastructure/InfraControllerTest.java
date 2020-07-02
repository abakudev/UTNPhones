package edu.utn.utnphones.controller.infrastructure;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.enums.TypeLine;
import edu.utn.utnphones.dto.CallRequestDto;
import org.mockito.Mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.mockito.MockitoAnnotations.initMocks;

public class InfraControllerTest {


    InfraController controller;
    CallRequestDto callDto;
    Call call;
    String sDate;
    Date date;

    City city1;
    City city2;


    @Mock
    CallController callController;



    public void setUp() throws ParseException {

        initMocks(this);
        controller = new InfraController(callController);

        city1 = new City((long)1, "Mar del Plata", "0223", null);
        city2 = new City((long)2, "Balcarce", "2266", null);

        sDate = "09/12/2018";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
        call = new Call((long)1, new PhoneLine((long)2, "5689632", TypeLine.mobile, true, null), new PhoneLine((long)3, "6995823", TypeLine.mobile, true, null), city1, city2, 125, 25, 23,  LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), false, null);
    }

//    @Test
//    public void testAddCallOk() throws ValidationException, ParseException {
//
//        callDto = new CallRequestDto("02235689632", "22666995823", 125, "09/12/2018");
//        when(callController.addCall(callDto)).thenReturn(call);
//        ResponseEntity response = controller.addCall(callDto);
//        assertEquals(200, response);
//
//    }
//

}