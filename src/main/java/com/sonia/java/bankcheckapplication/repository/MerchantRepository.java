package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.merchant.MonoBankMerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MerchantRepository extends JpaRepository<BankMerchantEntity, Long> {
}
