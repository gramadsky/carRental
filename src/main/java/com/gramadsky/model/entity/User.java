package com.gramadsky.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "User's name cannot be empty.")
    @Column
    private String name;
    @NotEmpty(message = "User's surname cannot be empty.")
    @Column
    private String surname;
    @Column(name = "dateofbirth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @NotEmpty(message = "User's email cannot be empty.")
    @Column(name = "email")
    private String email;
    @Column(name = "passportdata")
    private String passportData;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "user")
    private Login login;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    public User() {
    }

    public User(String name, String surname, LocalDate dateOfBirth, String email, String passportData, Status status) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.passportData = passportData;
        this.status = status;
    }

    public enum Status {
        NO_DEBTS("NO DEBTS"),
        INVOICE_NOT_PAID("INVOICE NOT PAID"),
        REPAIR_NOT_PAID("REPAIR NOT PAID"),
        BLOCKED("BLOCKED");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Override
    public String toString() {
        return id + ". " + name + ", " + surname +
                dateOfBirth + ", " + email;
    }
}

