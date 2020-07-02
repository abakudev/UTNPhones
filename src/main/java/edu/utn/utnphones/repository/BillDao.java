package edu.utn.utnphones.repository;

import edu.utn.utnphones.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BillDao extends JpaRepository<Bill, Long> {

    @Query(value = "select b.* from bills b\n" +
            "inner join telephone_lines tl on b.id_telephone_line = tl.id_telephone_line\n" +
            "where tl.id_user = :id  " , nativeQuery = true)
    List<Bill> getBillsByUserId(@Param("id") Long id);

    @Query(value =  "select b.* from bills b " +
                    "inner join telephone_lines tl on b.id_telephone_line = tl.id_telephone_line " +
                    "inner join users u on tl.id_user = u.id_user " +
                    "where u.dni = :dni " ,nativeQuery = true)
    List<Bill> getBillsByUserDni(@Param("dni") String dni);

    @Query(value = "select b.* from bills b\n" +
            "inner join telephone_lines tl on b.id_telephone_line = tl.id_telephone_line\n" +
            "where tl.id_user = :id and  b.date_bill between :from and :to " , nativeQuery = true)
    List<Bill> getBillsByUserLogged(@Param("id") Long id, @Param("from") LocalDateTime from , @Param("to") LocalDateTime to);

}
