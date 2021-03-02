package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.bank.category.UserCategoryLimit;
import com.sonia.java.bankcheckapplication.model.bank.category.UserCategoryLimitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCategoryLimitRepository extends JpaRepository<UserCategoryLimit, UserCategoryLimitId> {
}
