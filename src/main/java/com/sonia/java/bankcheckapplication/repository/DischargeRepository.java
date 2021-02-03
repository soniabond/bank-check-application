package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.bank.category.DischargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DischargeRepository extends JpaRepository<DischargeEntity, Long> {

    Optional<DischargeEntity> findByName(String name);
}
