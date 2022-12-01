package com.gramadsky.model.repository;

import com.gramadsky.model.entity.Car;
import com.gramadsky.model.entity.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByCarClassNameClass(String nameClass);

    List<Car> findAllByCarStatusLike(CarStatus carStatus);
}

