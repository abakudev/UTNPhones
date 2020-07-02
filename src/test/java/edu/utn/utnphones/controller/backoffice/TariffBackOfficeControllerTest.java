package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.TariffController;
import edu.utn.utnphones.domain.Tariff;
import edu.utn.utnphones.dto.TariffRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TariffBackOfficeControllerTest {

    Tariff tariff;
    TariffBackOfficeController tariffBackOfficeController;
    TariffRequestDto tariffRequestDto;

    @Mock
    TariffController tariffController;

    @Before
    public void setUp() {
        initMocks(this);
        tariffBackOfficeController = new TariffBackOfficeController(tariffController);
        tariff = new Tariff((long)1,4.0,6.0,null,null);
    }

    @Test
    public void createTariff() {
    }

    @Test
    public void readTariffOk() {
        List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff);
        when(tariffController.readTariff()).thenReturn(tariffs);
        ResponseEntity<List<Tariff>> responseEntity = tariffBackOfficeController.readTariff();

        assertEquals(responseEntity.getBody().size(),tariffs.size());
        assertEquals(responseEntity.getBody().get(0).getTariffId(),tariffs.get(0).getTariffId());
    }

    @Test
    public void readTariffNoContent(){

        List<Tariff> tariffs = new ArrayList<>();
        when(tariffController.readTariff()).thenReturn(tariffs);
        ResponseEntity<List<Tariff>> responseEntity = tariffBackOfficeController.readTariff();
        assertEquals(responseEntity.getStatusCodeValue(),204);
    }

    @Test
    public void updateTariffOk() {
        doNothing().when(tariffController).updateTariff(tariffRequestDto);
        ResponseEntity<Tariff> responseEntity = tariffBackOfficeController.updateTariff(tariffRequestDto);
        verify(tariffController, times(1)).updateTariff(tariffRequestDto);
        assertEquals(responseEntity, ResponseEntity.accepted().build());
    }

//    @Test
//    void updateTariffException() {
//
//        doNothing().when(tariffController).updateTariff(tariffRequestDto);
//        when(tariffBackOfficeController.updateTariff(tariffRequestDto)).thenThrow((new JpaSystemException(new RuntimeException(new SQLException()))));
//        ResponseEntity<Tariff> responseEntity = tariffBackOfficeController.updateTariff(tariffRequestDto);
//        assertEquals(responseEntity,ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }

    @Test
    public void deleteTariffOk() {

        doNothing().when(tariffController).deleteTariff(1,2);
        ResponseEntity<Tariff> responseEntity = tariffBackOfficeController.deleteTariff(1,2);
        verify(tariffController, times(1)).deleteTariff(1,2);
        assertEquals(responseEntity, ResponseEntity.ok().build());
    }

//    @Test
//    void deleteTariffException()  {
//
//        doNothing().when(tariffController).deleteTariff(1,2);
//        when(tariffBackOfficeController.deleteTariff(1,2)).thenThrow((new JpaSystemException(new RuntimeException(new SQLException()))));
//        ResponseEntity<Tariff> responseEntity = tariffBackOfficeController.deleteTariff(1,2);
//        assertEquals(responseEntity,ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }


}