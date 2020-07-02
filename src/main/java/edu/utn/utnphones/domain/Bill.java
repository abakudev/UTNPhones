package edu.utn.utnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private Long billId;

    @Column(name = "count_calls")
    private Integer countCalls;

    @Column(name = "cost_price")
    private Double costPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date_bill")
    private LocalDateTime dateBill;

    @Column(name = "expirate_date")
    private LocalDateTime expirationDate;

    @Column(name = "is_pay")
    private Boolean isPay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_telephone_line")
    @Fetch(FetchMode.JOIN)
    private PhoneLine phoneLine;

}
