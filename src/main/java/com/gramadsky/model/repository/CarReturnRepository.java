package com.gramadsky.model.repository;

import com.gramadsky.model.entity.CarReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarReturnRepository extends JpaRepository<CarReturn, Integer> {
}
