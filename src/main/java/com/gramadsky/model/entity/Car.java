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

    @Column(name = "totalcostcar")
    private Integer totalCostCar;

    @Column(name="year")
    private String year;

    @Column(name = "fuelconsumption")
    private Float fuelConsumption;

    @Column(name = "enginevolume")
    private Float engineVolume;

    @Column
    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Column
    private float cost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carstatus")
    private CarStatus carStatus;

    @Column(name="imagecar")
    private String imageCar;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return id + ". " + carModel + ", " + carClass +
                ", cost =" + cost + ", " + carStatus;
    }

    public  enum Transmission {
        AT("automatic transmission"),
        MT("mechanical transmission");

        private final String displayName;

        Transmission(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
