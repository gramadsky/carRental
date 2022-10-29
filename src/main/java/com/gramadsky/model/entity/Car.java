package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "carmodel")
    private String carModel;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carclass")
    private CarClass carClass;
    @Column
    private float cost;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "availability")
    private Availability availability;

    @OneToMany(mappedBy="car",cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    @Override
    public String toString() {
        return id + ". " + carModel + ", " + carClass +
                ", cost =" + cost + ", " + availability;
    }
}
