package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "amountofdays")
    private Integer amountOfDays;
    @Column
    private Integer percent;

}