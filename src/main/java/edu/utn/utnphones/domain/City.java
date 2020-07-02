package edu.utn.utnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "cities")
@Builder
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city")
    private Long cityId;

    @Column(name = "city_name")
    private String name;

    @Column(name = "area_code")
    private String areaCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_province", referencedColumnName="id_province", foreignKey = @ForeignKey(name = "fk_id_province"))
    @Fetch(FetchMode.JOIN)
    private Province province;

}
