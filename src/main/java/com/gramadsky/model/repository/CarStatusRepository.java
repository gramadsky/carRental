package com.gramadsky.model.repository;


import com.gramadsky.model.entity.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarStatusRepository extends JpaRepository<CarStatus, Integer> {

    CarStatus findByCarStatus(String name);
}
