package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "carclass")
public class CarClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "carclass")
    private String carClass;

    @Override
    public String toString() {
        return carClass;
    }
}
