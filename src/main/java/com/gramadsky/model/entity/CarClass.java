package com.gramadsky.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "carclass")
public class CarClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "carclass")
    private String nameClass;

    @OneToMany(mappedBy="carClass",cascade = CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();

    @Override
    public String toString() {
        return nameClass;
    }
}
