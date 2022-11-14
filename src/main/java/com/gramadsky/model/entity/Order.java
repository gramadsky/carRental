package com.gramadsky.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "request")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @Column(name = "startofrental")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startOfRental;
    @Column(name = "endofrental")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfRental;
    @Column
    private Float reduction;
    @Column
    private Float price;
    @Column
    private Status status;

    public  enum Status {
        CONFIRMED("CONFIRMED"),
        DENIED("DENIED"),
        PAID_WAITING_CONFIRMATION("PAID, WAITING CONFIRMATION"),
        WAITING_PAYMENT("WAITING PAYMENT");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
