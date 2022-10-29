package com.gramadsky.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
    @Column(name = "startofrental")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startOfRental;
    @Column(name = "endofrental")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfRental;
    @Column(name = "passportdata")
    private String passportData;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;
    @Column
    private float price;


    public Order() {
    }
}
