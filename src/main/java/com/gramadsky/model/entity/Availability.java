package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "availability")
public class Availability {
    @Id
    private Integer id;
    @Column
    private String availability;

    @Override
    public String toString() {
        return availability;
    }
}
