package com.gramadsky.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Login() {
    }

    public enum Role {
        ROLE_ADMIN(),
        ROLE_CLIENT();
    }
}


