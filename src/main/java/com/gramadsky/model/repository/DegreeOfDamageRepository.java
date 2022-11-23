package com.gramadsky.model.repository;

import com.gramadsky.model.entity.DegreeOfDamage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeOfDamageRepository extends JpaRepository<DegreeOfDamage, Integer> {
}
