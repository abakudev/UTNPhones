package edu.utn.utnphones.service;


import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.ClientRequestDto;
import edu.utn.utnphones.exceptions.CityNotFoundException;
import edu.utn.utnphones.exceptions.UserAlreadyExistsException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.repository.CityDao;
import edu.utn.utnphones.repository.UserDao;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;
    private final CityDao cityDao;

    public UserService(UserDao userDao, CityDao cityDao) {
        this.userDao = userDao;
        this.cityDao = cityDao;
    }

    public User addClient (ClientRequestDto newClient) throws UserAlreadyExistsException, CityNotFoundException, NoSuchAlgorithmException {

        City city = cityDao.getOne(newClient.getCityId());
        Optional.ofNullable(city).orElseThrow(()->new CityNotFoundException());

        User user = userDao.findByDni(newClient.getDni());
        if(user != null) throw new UserAlreadyExistsException();

        Long idUser = userDao.addClient(newClient.getCityId(), newClient.getFirstname(), newClient.getLastname(), newClient.getDni(), this.hashPwd(newClient.getPassword()), newClient.getTypeLine().name());
        return userDao.getOne(idUser);
    }

    public User getClientByDni(String dni) throws UserNotFoundException  {
        User user = userDao.findByDni(dni);
        return Optional.ofNullable(user).orElseThrow(() -> new UserNotFoundException());
    }

    public List<User> getAllClients(){
        return userDao.findAllClients();
    }

    public User login(String dni, String password) throws UserNotFoundException, NoSuchAlgorithmException {

        User user = userDao.getUserByDniAndPwd(dni,  this.hashPwd(password));
        return Optional.ofNullable(user).orElseThrow(() -> new UserNotFoundException());
    }

    public String hashPwd(String pass) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] data = pass.getBytes();
        m.update(data, 0, data.length);
        BigInteger i = new BigInteger(1, m.digest());
        return String.format("%1$032X", i);
    }

    public User updateClient(String dni, ClientRequestDto newClient) throws CityNotFoundException, UserNotFoundException {

        City city = cityDao.getOne(newClient.getCityId());
        Optional.ofNullable(city).orElseThrow(() -> new CityNotFoundException());

        User existingUser = userDao.findByDni(dni);
        Optional.ofNullable(existingUser).orElseThrow(() -> new UserNotFoundException());

        existingUser.setCity( city );
        existingUser.setFirstname( (newClient.getFirstname() != null) ? newClient.getFirstname() : existingUser.getFirstname() );
        existingUser.setLastname( (newClient.getLastname() != null) ? newClient.getLastname() : existingUser.getLastname() );

        Long id = userDao.updateClient(existingUser.getCity().getCityId(), existingUser.getFirstname(), existingUser.getLastname(), dni);

        return userDao.getOne(id);
    }

    public void deleteClient(String dni) throws UserNotFoundException {

        User user = userDao.findByDni(dni);
        Optional.ofNullable(user).orElseThrow(() -> new UserNotFoundException());
        userDao.removeClientByDni(dni);
    }

}

