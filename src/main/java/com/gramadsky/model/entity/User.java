package com.gramadsky.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
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
    @Column
    private String name;
    @Column
    private String surname;
    @Column(name = "dateofbirth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private String status;

    @OneToOne(mappedBy = "user")
    private Login login;

    @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    public User() {
    }

    @Override
    public String toString() {
        return id + ". " + name + ", " + surname +
                 dateOfBirth + ", " + email;
    }
}

