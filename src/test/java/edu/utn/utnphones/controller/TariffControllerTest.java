package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Tariff;
import edu.utn.utnphones.dto.TariffRequestDto;
import edu.utn.utnphones.exceptions.CityNotFoundException;
import edu.utn.utnphones.exceptions.TarriffAlreadyExistsException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.service.TariffService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TariffControllerTest {

    TariffController tariffController;
    Tariff tariff;
    TariffRequestDto tariffRequestDto;
    @Mock
    TariffService tariffService;

    @Before
    public void setUp() {
        initMocks(this);
        tariffController = new TariffController(tariffService);
        tariff = new Tariff();
    }

    @Test
    public void createTariffOk() throws TarriffAlreadyExistsException, CityNotFoundException, ValidationException {
        TariffRequestDto tariffRequestDto = new TariffRequestDto(3.0,6.0,(long)4,(long)7);
        when(tariffService.createTariff(tariffRequestDto)).thenReturn(tariff);
        Tariff tariff1 = tariffController.createTariff(tariffRequestDto);
        assertEquals(tariff1,tariff);
    }

    @Test
    public void createTariffCityNotFound() throws TarriffAlreadyExistsException, CityNotFoundException, ValidationException {

        TariffRequestDto tariffRequestDto = new TariffRequestDto(3.0,6.0,(long)4,(long)7);
        when(tariffService.createTariff(tariffRequestDto)).thenThrow((new CityNotFoundException()));
        assertThrows(CityNotFoundException.class, () -> tariffController.createTariff(tariffRequestDto));
    }

    @Test(expected = ValidationException.class)
    public void createTariffCityInvalidData() throws TarriffAlreadyExistsException, CityNotFoundException, ValidationException {

        TariffRequestDto tariffRequestDto = new TariffRequestDto(6.0,3.0,(long)4,(long)7);
        when(tariffService.createTariff(tariffRequestDto)).thenThrow(ValidationException.class);
        tariffController.createTariff(tariffRequestDto);
    }

    @Test(expected = ValidationException.class)
    public void createTariffCityInvalidData2() throws TarriffAlreadyExistsException, CityNotFoundException, ValidationException {

        TariffRequestDto tariffRequestDto = new TariffRequestDto(null,3.0,(long)4,(long)7);
        when(tariffService.createTariff(tariffRequestDto)).thenThrow(ValidationException.class);
        tariffController.createTariff(tariffRequestDto);
    }

    @Test(expected = ValidationException.class)
    public void createTariffCityInvalidData3() throws TarriffAlreadyExistsException, CityNotFoundException, ValidationException {

        TariffRequestDto tariffRequestDto = new TariffRequestDto(3.5,null,(long)4,(long)7);
        when(tariffService.createTariff(tariffRequestDto)).thenThrow(ValidationException.class);
        tariffController.createTariff(tariffRequestDto);
    }

    @Test
    public void readTariffOk() {

        List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff);
        when(tariffService.readTariff()).thenReturn(tariffs);
        List<Tariff> tariffs2 = tariffController.readTariff();
        assertEquals(tariffs.get(0),tariffs2.get(0));
        assertEquals(tariffs.size(),tariffs2.size());
    }

    @Test
    public void updateTariffOk() {
        doNothing().when(tariffService).updateTariff(tariffRequestDto);
        tariffController.updateTariff(tariffRequestDto);
        verify(tariffService, times(1)).updateTariff(tariffRequestDto);

    }

    @Test
    public void deleteTariff() {
        doNothing().when(tariffService).deleteTariff(1,2);
        tariffController.deleteTariff(1,2);
        verify(tariffService, times(1)).deleteTariff(1,2);
    }
    

}