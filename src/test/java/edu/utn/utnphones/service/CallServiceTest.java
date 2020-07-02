package edu.utn.utnphones.service;

import edu.utn.utnphones.domain.*;
import edu.utn.utnphones.domain.enums.TypeLine;
import edu.utn.utnphones.dto.CallRequestDto;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.projection.CallView;
import edu.utn.utnphones.repository.CallDao;
import edu.utn.utnphones.repository.CityDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

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

public class CallServiceTest {

    CallService callService;
    CallRequestDto callDto;

    CallView callView;
    List<CallView> callViewList;

    @Mock
    CityDao cityDao;
    @Mock
    CallDao callDao;
    @Mock
    UserService userService;

    //Dates
    String from;
    String to;
    Date fromDate;
    Date toDate;

    @Before
    public void setUp() throws ParseException {

        initMocks(this);
        callService = new CallService(callDao, cityDao, userService);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        callView = factory.createProjection(CallView.class);

        callView.setDni("32165498");
        callView.setPhoneNumberOrigin("022365899633");
        callView.setPhoneNumberDestination("02203658896");
        callView.setCityOrigin("Mar del Plata");
        callView.setCityDestination("Merlo");
        callView.setDuration(125);
        callView.setDate(new Date());

        List<CallView> callViewList = new ArrayList<>();
        callViewList.add(callView);
        from = "01/02/2003";
        to = "09/12/2018";

        fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(from);
        toDate = new SimpleDateFormat("dd/MM/yyyy").parse(to);
    }


    /* test method : addCall */
    @Test
    public void testAddCallOk() throws ParseException {

        City cityOrigin = new City((long)1,"Mar del Plata", "0220", new Province((long)1, "Buenos Aires"));
        City cityDestination = new City((long)25, "Cordoba","0351", new Province((long)5, "Cordoba"));
        PhoneLine phoneLineOrigin = new PhoneLine((long)1, "2569369", TypeLine.mobile, true , null );
        PhoneLine phoneLineDestination = new PhoneLine( (long)2, "2569369", TypeLine.mobile, true, null );
        Call call = new Call((long)1, phoneLineOrigin, phoneLineDestination, cityOrigin, cityDestination , 68, 50, 48, LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault()), false, null) ;

        callDto = new CallRequestDto("02232569369","03512569369",123,"12/09/2020");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(callDto.getDate());

        when(callDao.saveCall(callDto.getNumberOrigin(), callDto.getNumberDestination(), callDto.getDuration(),date)).thenReturn((long)132);
        when(callDao.getOne((long)132)).thenReturn(call);

        Call returnedCall = callService.addCall(callDto);

        assertEquals(call, returnedCall);
        verify(callDao, times(1)).saveCall(callDto.getNumberOrigin(), callDto.getNumberDestination(), callDto.getDuration(),date);
    }

    @Test(expected = ParseException.class)
    public void testAddCallParseException() throws ParseException {
        City cityOrigin = new City((long)1,"Mar del Plata", "0220", new Province((long)1, "Buenos Aires"));
        City cityDestination = new City((long)25, "Cordoba","0351", new Province((long)5, "Cordoba"));
        PhoneLine phoneLineOrigin = new PhoneLine((long)1, "2569369", TypeLine.mobile, true , null );
        PhoneLine phoneLineDestination = new PhoneLine( (long)2, "2569369", TypeLine.mobile, true, null );
        Call call = new Call((long)1, phoneLineOrigin, phoneLineDestination, cityOrigin, cityDestination , 68, 50, 48, LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault()), false, null) ;

        callDto = new CallRequestDto("02232569369","03512569369",123,"12/09/2020");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(callDto.getDate());

        when(callDao.saveCall(callDto.getNumberOrigin(), callDto.getNumberDestination(), callDto.getDuration(), date)).thenThrow(ParseException.class);
        callService.addCall(callDto);
    }


    /* test method : getCallsByDni */
    @Test
    public void testGetCallsByDniOk() throws UserNotFoundException {

        User user = new User();
        user.setDni("12345678");

        when(userService.getClientByDni("12345678")).thenReturn(user);
        when(callDao.getCallsByDni("12345678")).thenReturn(callViewList);

        List<CallView> callViewsReturned = callService.getCallsByDni("12345678");

        assertEquals(callViewList, callViewsReturned);

        verify(callDao, times(1)).getCallsByDni("12345678");
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetCallsByDniUserNotFound() throws UserNotFoundException {

      when(userService.getClientByDni("12345678")).thenThrow(UserNotFoundException.class);
      callService.getCallsByDni("12345678");
    }

    /* test method : getCallsByUserFilterByDate */
    @Test
    public void testGetCallsByUserFilterByDateOk() {

        when(callDao.getCallsByUserFilterByDate("32165498", LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(toDate.toInstant(), ZoneId.systemDefault()) )).thenReturn(callViewList);

        List<CallView> callViewsReturned = callDao.getCallsByUserFilterByDate("32165498", LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(toDate.toInstant(), ZoneId.systemDefault()));

        Assertions.assertEquals(callViewList, callViewsReturned);
        verify(callDao, times(1)).getCallsByUserFilterByDate("32165498",  LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(toDate.toInstant(), ZoneId.systemDefault()));
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetCallsByUserFilterByDateUserNotFound() throws UserNotFoundException {

        when(callDao.getCallsByUserFilterByDate("32165498", LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault()), LocalDateTime.ofInstant(toDate.toInstant(), ZoneId.systemDefault()) )).thenThrow(UserNotFoundException.class);
        callService.getCallsByUserFilterByDate("32165498", fromDate, toDate);
    }

    /* test method : getTOP10MostCalledDestination */
    @Test
    public void testGetTOP10MostCalledDestinationOk(){

        City city = new City((long) 3,"Mar del Plata", "0223", new Province((long)1 ,"Buenos Aires"));
        List<City> cities = new ArrayList<>();
        cities.add(city);

        when(cityDao.getTOP10MostCalledDestination((long)45)).thenReturn(cities);

        List<City>returnedCities = callService.getTOP10MostCalledDestination((long)45);

        Assertions.assertEquals(cities, returnedCities);
        verify(cityDao, times(1)).getTOP10MostCalledDestination((long)45);
    }


}