package edu.utn.utnphones.service;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.dto.CallRequestDto;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.projection.CallView;
import edu.utn.utnphones.repository.CallDao;
import edu.utn.utnphones.repository.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CallService {

    private final CallDao callDao;
    private final CityDao cityDao;
    private final UserService userService;

    @Autowired
    public CallService(CallDao callDao, CityDao cityDao, UserService userService) {
        this.callDao = callDao;
        this.cityDao = cityDao;
        this.userService = userService;
    }

    public Call addCall(CallRequestDto call) throws ParseException {

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(call.getDate());
        Long idCall = callDao.saveCall(call.getNumberOrigin(), call.getNumberDestination(), call.getDuration(), date );
        return callDao.getOne(idCall);
    }

    public List<CallView> getCallsByDni(String dni) throws UserNotFoundException {
        userService.getClientByDni(dni);
        return callDao.getCallsByDni(dni);
    }

    public List<CallView> getCallsByUserFilterByDate(String dni, Date from, Date to) throws UserNotFoundException {
        userService.getClientByDni(dni);
        return callDao.getCallsByUserFilterByDate(dni, LocalDateTime.ofInstant(from.toInstant(),
                ZoneId.systemDefault()), LocalDateTime.ofInstant(to.toInstant(),
                ZoneId.systemDefault()));
    }

    public List<City> getTOP10MostCalledDestination(Long id) {
        return cityDao.getTOP10MostCalledDestination(id);
    }

}
