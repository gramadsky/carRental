package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "carstatus")
public class CarStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "carstatus")
    private String carStatus;

    @OneToMany(mappedBy="carStatus",cascade = CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();

    @Override
    public String toString() {
        return carStatus;
    }
}
