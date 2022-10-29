package com.gramadsky.security.repositories;

import com.gramadsky.model.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Login, Integer> {
    Optional<Login> findByUsername(String login);

}