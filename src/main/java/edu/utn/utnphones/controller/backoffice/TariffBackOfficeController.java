package edu.utn.utnphones.controller.backoffice;


import edu.utn.utnphones.controller.TariffController;
import edu.utn.utnphones.domain.Tariff;
import edu.utn.utnphones.dto.TariffRequestDto;
import edu.utn.utnphones.exceptions.CityNotFoundException;
import edu.utn.utnphones.exceptions.TarriffAlreadyExistsException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.utils.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/backoffice/tariffs")
public class TariffBackOfficeController {

    private final TariffController tariffController;

    @Autowired
    public TariffBackOfficeController(TariffController tariffController) {
        this.tariffController = tariffController;
    }

    @PostMapping
    public ResponseEntity createTariff(@RequestBody TariffRequestDto newTariff) throws CityNotFoundException, ValidationException, JpaSystemException, TarriffAlreadyExistsException {
        Tariff tariff = tariffController.createTariff(newTariff);
        return ResponseEntity.created(UriUtils.getLocation(tariff.getTariffId())).build();
    }

    @GetMapping
    public ResponseEntity<List<Tariff>> readTariff(){
        List<Tariff> tariffs = tariffController.readTariff();
        return (tariffs.size() > 0) ? ResponseEntity.ok(tariffs) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity updateTariff(@RequestBody TariffRequestDto tariff){
        tariffController.updateTariff(tariff);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{idCityOrigin}/{idCityDestination}")
    public ResponseEntity deleteTariff(@PathVariable Integer idCityOrigin, @PathVariable Integer idCityDestination){
        tariffController.deleteTariff(idCityOrigin,idCityDestination);
        return ResponseEntity.ok().build();
    }





}
