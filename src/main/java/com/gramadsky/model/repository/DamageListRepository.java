package com.gramadsky.model.repository;

import com.gramadsky.model.entity.DamageList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageListRepository extends JpaRepository<DamageList, Integer> {
}
