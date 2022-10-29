package com.gramadsky.model.repository;

import com.gramadsky.model.entity.CarCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarConditionRepository extends JpaRepository<CarCondition, Integer> {
}
