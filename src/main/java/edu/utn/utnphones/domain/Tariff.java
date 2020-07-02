package edu.utn.utnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tariffs")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tariff")
    private Long tariffId;

    @Column(name = "cost_price")
    private Double costPrice;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_city_origin")
    @Fetch(FetchMode.JOIN)
    City cityOrigin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_city_destination")
    @Fetch(FetchMode.JOIN)
    City cityDestination;

}
