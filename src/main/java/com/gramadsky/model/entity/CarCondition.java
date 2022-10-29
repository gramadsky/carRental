package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CarCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String condition;
}
