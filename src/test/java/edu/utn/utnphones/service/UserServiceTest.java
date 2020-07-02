package edu.utn.utnphones.service;


import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.enums.Role;
import edu.utn.utnphones.domain.enums.TypeLine;
import edu.utn.utnphones.dto.ClientRequestDto;
import edu.utn.utnphones.exceptions.CityNotFoundException;
import edu.utn.utnphones.exceptions.InvalidLoginException;
import edu.utn.utnphones.exceptions.UserAlreadyExistsException;
import edu.utn.utnphones.exceptions.UserNotFoundException;
import edu.utn.utnphones.repository.CityDao;
import edu.utn.utnphones.repository.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    UserService userService;
    City city;
    User user;
    List<User> userList;
    ClientRequestDto clientDto;

    @Mock
    private UserDao userDao;
    @Mock
    private CityDao cityDao;

    @Before
    public void setUp() {
        initMocks(this);
        userService = new UserService(userDao, cityDao);

        city = new City((long)1,"Mar del plata","0223",null);
        user = new User((long) 1, "98765421", "Juan", "Perez", "321asd", true, Role.client, city);
        userList = new ArrayList<User>();
        userList.add(user);
    }

    @Test
    public void testLoginOk() throws UserNotFoundException, NoSuchAlgorithmException, InvalidLoginException {
        User loggedUser = new User((long) 1, "98765421", "Juan", "Perez", "pwd", true, Role.client, city);;

        when(userDao.getUserByDniAndPwd("98765421", userService.hashPwd("321asd"))).thenReturn(loggedUser);
        User returnedUser = userService.login("98765421","321asd");
        assertEquals(loggedUser.getUserId(), returnedUser.getUserId());
        assertEquals(loggedUser.getDni(), returnedUser.getDni());
        assertEquals(loggedUser.getPassword(), returnedUser.getPassword());

        verify(userDao, times(1)).getUserByDniAndPwd("98765421", userService.hashPwd("321asd"));
    }

    @Test(expected = UserNotFoundException.class)
    public void testLoginUserNotFound() throws UserNotFoundException, NoSuchAlgorithmException {

        when(userDao.getUserByDniAndPwd("user",userService.hashPwd("pwd") )).thenReturn(null);
        userService.login("user", "pwd");
    }

    @Test
    public void addClientUserOk() throws NoSuchAlgorithmException, UserAlreadyExistsException, CityNotFoundException {

        ClientRequestDto clientDto = new ClientRequestDto((long)1, "Joaquin", "Lopez", "98765421", "pwd123", TypeLine.home);
        when(cityDao.getOne(clientDto.getCityId()) ).thenReturn(city);
        when(userDao.findByDni("98765421")).thenReturn(null);
        userService.addClient(clientDto);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void addClientUserAlreadyExist() throws NoSuchAlgorithmException, UserAlreadyExistsException, CityNotFoundException {

        ClientRequestDto clientDto = new ClientRequestDto((long)1, "Joaquin", "Lopez", "98765421", "pwd123", TypeLine.home);
        when(cityDao.getOne(clientDto.getCityId()) ).thenReturn(city);
        when(userDao.findByDni("98765421")).thenReturn(user);
        userService.addClient(clientDto);
    }

    @Test(expected = CityNotFoundException.class)
    public void addClientCityNotFound() throws NoSuchAlgorithmException, UserAlreadyExistsException, CityNotFoundException {

        ClientRequestDto clientDto = new ClientRequestDto((long) 1234, "Abel", "Acuña", "123456","321asd", TypeLine.home);
        when(cityDao.getOne(clientDto.getCityId()) ).thenReturn(null);
        userService.addClient(clientDto);
    }

    @Test
    public void getClientByDniOk() throws UserNotFoundException {
        when(userDao.findByDni("4333444")).thenReturn(user);
        User user2 = userService.getClientByDni("4333444");
        assertEquals(user2,user);
    }

    @Test(expected = UserNotFoundException.class)
    public void getClientByDniUserNotFound() throws UserNotFoundException {
        when(userDao.findByDni("123")).thenReturn(null);
        userService.getClientByDni("123");
    }

    @Test
    public void getAllClientsOk() {
        when(userDao.findAllClients()).thenReturn(userList);
        List<User> returnedUser = userService.getAllClients();
        assertEquals(userList, returnedUser);
        verify(userDao, times(1)).findAllClients();
    }

    @Test
    public void getAllClientsEmpty(){
        List<User> emptyList = new ArrayList<>();
        when(userDao.findAllClients()).thenReturn(emptyList);
        List<User> returnedUser = userService.getAllClients();
        assertEquals(emptyList, returnedUser);
        verify(userDao, times(1)).findAllClients();
    }

    @Test
    public void testUpdateClientOk() throws UserNotFoundException, CityNotFoundException {

        ClientRequestDto clientDto = new ClientRequestDto((long)1, "Joaquin", "Perez", "98765421", "321654", TypeLine.home);

        when(cityDao.getOne(user.getCity().getCityId())).thenReturn(city);
        when(userDao.findByDni(user.getDni())).thenReturn(user);
        when(userDao.updateClient(clientDto.getCityId(), clientDto.getFirstname(), clientDto.getLastname(), clientDto.getDni())).thenReturn((long)25);
        when(userDao.getOne((long)25)).thenReturn(user);
        User modifyUser =  userService.updateClient("98765421",clientDto);

        assertEquals(user.getUserId(), modifyUser.getUserId());
        verify(userDao, times(1)).updateClient(clientDto.getCityId(), clientDto.getFirstname(), clientDto.getLastname(), clientDto.getDni());
    }

    @Test
    public void testUpdateClientFirtsnameEmpty() throws UserNotFoundException, CityNotFoundException {

        ClientRequestDto clientDto = new ClientRequestDto((long)1, null, "Perez", "98765421", "321654", TypeLine.home);

        when(cityDao.getOne(user.getCity().getCityId())).thenReturn(city);
        when(userDao.findByDni(user.getDni())).thenReturn(user);
        when(userDao.updateClient(clientDto.getCityId(), user.getFirstname(), clientDto.getLastname(), clientDto.getDni())).thenReturn((long)25);
        when(userDao.getOne((long)25)).thenReturn(user);
        User modifyUser =  userService.updateClient("98765421",clientDto);

        assertEquals(user.getUserId(), modifyUser.getUserId());
        verify(userDao, times(1)).updateClient(clientDto.getCityId(), user.getFirstname(), clientDto.getLastname(), clientDto.getDni());
    }

    @Test
    public void testUpdateClientLastNameEmpty() throws UserNotFoundException, CityNotFoundException {

        ClientRequestDto clientDto = new ClientRequestDto((long)1, "Jose", null, "98765421", "321654", TypeLine.home);

        when(cityDao.getOne(user.getCity().getCityId())).thenReturn(city);
        when(userDao.findByDni(user.getDni())).thenReturn(user);
        when(userDao.updateClient(clientDto.getCityId(), clientDto.getFirstname(), user.getLastname(), clientDto.getDni())).thenReturn((long)25);
        when(userDao.getOne((long)25)).thenReturn(user);
        User modifyUser =  userService.updateClient("98765421",clientDto);

        assertEquals(user.getUserId(), modifyUser.getUserId());
        verify(userDao, times(1)).updateClient(clientDto.getCityId(), clientDto.getFirstname(),  user.getLastname(), clientDto.getDni());
    }

    @Test(expected = CityNotFoundException.class)
    public void testUpdateCityNotFound() throws UserNotFoundException, CityNotFoundException {

        ClientRequestDto clientDto = new ClientRequestDto((long)55, "Joaquin", "Perez", "98765421", "321654", TypeLine.home);
        when(cityDao.getOne(clientDto.getCityId())).thenReturn(null);
        userService.updateClient("98765421" ,clientDto);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdateUserNotFound() throws UserNotFoundException, CityNotFoundException {
        ClientRequestDto clientDto = new ClientRequestDto((long)55, "Joaquin", "Perez", "98765421", "321654", TypeLine.home);
        when(cityDao.getOne(clientDto.getCityId())).thenReturn(city);
        when(userDao.findByDni(clientDto.getDni())).thenReturn(null);
        userService.updateClient(clientDto.getDni() ,clientDto);
    }

    @Test
    public void testDeleteOk() throws UserNotFoundException {

        when(userDao.findByDni(user.getDni())).thenReturn(user);
        doNothing().when(userDao).removeClientByDni(user.getDni());
        userService.deleteClient(user.getDni());
    }

    @Test(expected = UserNotFoundException.class )
    public void testDeleteUserNotFound() throws UserNotFoundException {

        when(userDao.findByDni("12345")).thenReturn(null);
        userService.deleteClient("12345");
    }




}