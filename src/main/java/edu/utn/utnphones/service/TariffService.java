package edu.utn.utnphones.service;

import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Tariff;
import edu.utn.utnphones.dto.TariffRequestDto;
import edu.utn.utnphones.exceptions.CityNotFoundException;
import edu.utn.utnphones.exceptions.TarriffAlreadyExistsException;
import edu.utn.utnphones.repository.CityDao;
import edu.utn.utnphones.repository.TariffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    private static TariffDao tariffDao;
    private final CityDao cityDao;

    @Autowired
    public TariffService(TariffDao tariffDao, CityDao cityDao) {
        this.tariffDao = tariffDao;
        this.cityDao = cityDao;
    }

    /*CREATE*/
    public Tariff createTariff(TariffRequestDto newTariff) throws CityNotFoundException, TarriffAlreadyExistsException {

        City cityOrigin = cityDao.getById(newTariff.getCityOriginId());
        Optional.ofNullable(cityOrigin).orElseThrow(() -> new CityNotFoundException());
        City cityDestination = cityDao.getById(newTariff.getCityDestinationId());
        Optional.ofNullable(cityDestination).orElseThrow(() -> new CityNotFoundException());
        Tariff tariff = tariffDao.getByIdCities( newTariff.getCityOriginId(), newTariff.getCityDestinationId() );
        if(tariff == null){
            tariffDao.create(cityOrigin.getCityId(), cityDestination.getCityId(), newTariff.getCostPrice(), newTariff.getPrice());
            return tariffDao.getByIdCities(cityOrigin.getCityId(), cityDestination.getCityId());
        }else{
            throw new TarriffAlreadyExistsException();
        }
    }

    /*READ*/
    public List<Tariff> readTariff(){
        return tariffDao.getAllTariffs();
    }

    /*UPDATE*/
    public void updateTariff(TariffRequestDto tariff) {
        tariffDao.update(tariff.getCityOriginId(), tariff.getCityDestinationId(),
                tariff.getCostPrice(),tariff.getPrice());
    }

    /*DELETE*/
    public void deleteTariff(Integer idCityOrigin, Integer idCityDestination){
        tariffDao.delete(idCityOrigin,idCityDestination);
    }


}
