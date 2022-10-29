package com.gramadsky.model.repository;

import com.gramadsky.model.entity.RepairBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairBillRepository extends JpaRepository<RepairBill, Integer> {
}
