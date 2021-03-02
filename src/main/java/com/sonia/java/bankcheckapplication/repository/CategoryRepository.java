package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);


}
