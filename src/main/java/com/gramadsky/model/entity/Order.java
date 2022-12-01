package com.gramadsky.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(name = "repairbill")
    private Integer repairBill;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "creationtime")
    private LocalDateTime creationTime;

    public enum Status {
        CONFIRMED("CONFIRMED"),
        DENIED("DENIED"),
        PAID_WAITING_CONFIRMATION("PAID, WAITING CONFIRMATION"),
        WAITING_PAYMENT("WAITING PAYMENT"),
        CAR_RETURN("CAR RETURN"),
        CAR_DAMAGED("CAR DAMAGED"),
        WAITING_FOR_PAYMENTS_FOR_REPAIR("WAITING FOR PAYMENTS FOR REPAIR"),
        EXPIRED("EXPIRED"),
        COMPLETED("COMPLETED");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
