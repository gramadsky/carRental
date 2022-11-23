package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "degreeofdamage")
public class DegreeOfDamage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column(name = "damagepercentage")
    private Integer damagePercentage;
}
