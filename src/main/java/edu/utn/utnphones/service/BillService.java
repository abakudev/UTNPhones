package edu.utn.utnphones.service;

import edu.utn.utnphones.domain.Bill;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.repository.BillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BillService {

    private final BillDao billDao;
    private final UserService userService;

    @Autowired
    public BillService(BillDao billDao, UserService userService) {
        this.billDao = billDao;
        this.userService = userService;
    }

    public List<Bill> getBillsByUserId(Long id) {
        return billDao.getBillsByUserId(id);
    }

    public List<Bill> getBillsByUserDni(String dni) throws UserNotFoundException {
        userService.getClientByDni(dni);
        return billDao.getBillsByUserDni(dni);
    }

    public List<Bill> getBillsByUserLogged(Long id, Date from , Date to) {

        return billDao.getBillsByUserLogged(id, LocalDateTime.ofInstant(from.toInstant(),
                ZoneId.systemDefault()) ,LocalDateTime.ofInstant(to.toInstant(),
                ZoneId.systemDefault()));
    }

}
