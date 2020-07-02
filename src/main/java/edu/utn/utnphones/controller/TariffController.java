package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Tariff;
import edu.utn.utnphones.dto.TariffRequestDto;
import edu.utn.utnphones.exceptions.CityNotFoundException;
import edu.utn.utnphones.exceptions.TarriffAlreadyExistsException;
import edu.utn.utnphones.exceptions.ValidationException;
import edu.utn.utnphones.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TariffController {

    private final TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    public Tariff createTariff (TariffRequestDto tariff) throws CityNotFoundException, ValidationException, TarriffAlreadyExistsException {

        if(tariff.getPrice()==null || tariff.getCostPrice()==null ){
            throw new ValidationException("Error - cost or price, cannot be null");
        }else if(tariff.getCostPrice() <= tariff.getPrice()) {
            return tariffService.createTariff(tariff);
        }else{
            throw new ValidationException("Error - the price must be a most value than cost");
        }
    }

    public List<Tariff> readTariff(){
        return  tariffService.readTariff();
    }

    public void updateTariff ( TariffRequestDto tariff){
        tariffService.updateTariff(tariff);
    }


    public void deleteTariff(Integer idCityOrigin, Integer idCityDestination){
        tariffService.deleteTariff(idCityOrigin,idCityDestination);
    }





}

