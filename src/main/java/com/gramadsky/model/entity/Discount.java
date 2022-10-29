package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Long amountOfDays;
    @Column
    private Integer discount;

    @OneToMany(mappedBy="discount",cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    public Discount setAmountOfDays(Long amountOfDays) {
        this.amountOfDays = amountOfDays;
        return this;
    }
}