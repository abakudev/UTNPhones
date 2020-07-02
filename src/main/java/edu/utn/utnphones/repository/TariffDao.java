package edu.utn.utnphones.repository;

import edu.utn.utnphones.domain.Tariff;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

public interface TariffDao extends JpaRepository <Tariff, Integer> {

    /*CREATE*/
    @Procedure(procedureName = "sp_insert_tariff")
    void create(@Param("pId_city_origin") Long idCityOrigin,
                         @Param("pId_city_destination") Long idCityDestination,
                         @Param("pCost_price") Double costPrice,
                         @Param("pPrice") Double price) throws DataAccessException;



    /*READ*/
    @Query(value = "SELECT t.* FROM tariffs t ", nativeQuery = true)
    List<Tariff> getAllTariffs()throws JpaSystemException;


    @Query(value = "SELECT t.* FROM tariffs t t.id_tariff = :id", nativeQuery = true)
    Tariff getById(@Param("id") Long id);

    @Query(value = "select t.* from tariffs t where t.id_city_origin = :id_origin  and t.id_city_destination = :id_destination ", nativeQuery = true)
    Tariff getByIdCities(@Param("id_origin") Long id_origin, @Param("id_destination") Long id_destination);

        /*UPDATE*/
    @Procedure(procedureName = "sp_update_tariff")
    public void update(@Param("pId_city_origin") Long idCityOrigin,
                       @Param("pId_city_destination") Long idCityDestination,
                       @Param("pCost_price") Double costPrice,
                       @Param("pPrice") Double price);

    /*DELETE*/
    @Procedure(procedureName = "sp_delete_tariff")
    public  void delete(@Param("pId_city_origin") Integer idCityOrigin,
                        @Param("pId_city_destination") Integer idCityDestination);



}
