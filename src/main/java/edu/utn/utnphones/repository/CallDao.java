package edu.utn.utnphones.repository;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.projection.CallView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CallDao extends JpaRepository<Call, Long> {

    @Procedure( procedureName = "sp_insert_call", outputParameterName = "pIdCall")
    Long saveCall( @Param("pPhoneNumberOrigin") String phoneLineOrigin, @Param("pPhoneNumberDestination") String phoneLineDestination , @Param("pDuration") Integer duration,  @Param("pDate") Date date );


    @Query(value = "select dni , phoneNumberOrigin, phoneNumberDestination, cityOrigin, cityDestination, " +
            "duration, date, totalPrice from v_calls where dni = :dni", nativeQuery = true)
    List<CallView> getCallsByDni(@Param("dni") String dni);

    @Query(value = "select dni , phoneNumberOrigin, phoneNumberDestination, cityOrigin, cityDestination, " +
            "duration, date, totalPrice from v_calls where dni = :dni and date between :from and :to ", nativeQuery = true)
    List<CallView>  getCallsByUserFilterByDate (String dni, LocalDateTime from, LocalDateTime to);

}
