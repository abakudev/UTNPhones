package edu.utn.utnphones.repository;

import edu.utn.utnphones.domain.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhoneLineDao extends JpaRepository<PhoneLine,Long> {


    @Transactional
    @Procedure(procedureName = "sp_generate_phone_line_by_dni", outputParameterName = "pIdPhone")
    Long generateNumber(@Param("pUserDni")String dni, @Param("pTypeLine") String typeLine);

    //delete
    @Transactional
    @Procedure(procedureName = "sp_suspend_phone_line")
    void  suspendPhoneLine(@Param("pIdPhoneLine") Long IdPhoneLine);

    //update
    @Transactional
    @Procedure(procedureName = "sp_active_phone_line", outputParameterName = "pIdPhone")
    Long  activePhoneLine(@Param("pIdPhoneLine") Long IdPhoneLine);


    @Query(value = "SELECT p FROM PhoneLine p JOIN p.user u WHERE u.dni = :dni")
    List<PhoneLine> getPhoneLinesByDni(@Param("dni")String dni);

    @Query(value = "select p.* from telephone_lines p WHERE p.enabled = true", nativeQuery = true)
    List<PhoneLine> getPhoneLines();



}

