package edu.utn.utnphones.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.utn.utnphones.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long userId;

    private String dni;

    private String firstname;

    private String lastname;

    @Column(name = "pwd")
    @JsonIgnore
    private String password;

    @JsonIgnore
    private Boolean enabled;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_city")
    @Fetch(FetchMode.JOIN)
    private City city;

}
