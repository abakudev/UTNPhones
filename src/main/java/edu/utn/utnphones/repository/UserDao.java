package edu.utn.utnphones.repository;

import edu.utn.utnphones.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

    @Procedure(procedureName = "sp_insert_user_client", outputParameterName = "pIdUser")
    Long addClient(@Param("pIdCity") Long idCity , @Param("pFirstname") String firstname, @Param("pLastname") String lastname, @Param("pDni") String dni, @Param("pPwd") String pwd, @Param("pTypeLine") String typeLine );

    @Query(value = "SELECT u FROM User u WHERE u.dni = :dni and u.password = :pwd")
    User getUserByDniAndPwd(@Param("dni") String dni, @Param("pwd") String password);

    @Query(value = "SELECT u FROM User u WHERE u.dni = :dni")
    User findByDni(String dni);

    @Query(value = "SELECT u.* FROM users u WHERE u.user_role = 'client' and u.enabled = true" , nativeQuery = true)
    List<User> findAllClients();

    @Procedure(procedureName = "sp_update_user", outputParameterName = "pIdUser")
    Long updateClient(@Param("pIdCity") Long idCity , @Param("pFirstname") String firstname, @Param("pLastname") String lastname, @Param("pDni") String dni);

    @Procedure(procedureName = "sp_delete_user")
    void removeClientByDni(@Param("pDni") String dni );

}


