package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.user.CardChekingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CardChekingUser, Long> {

    Optional<CardChekingUser> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
