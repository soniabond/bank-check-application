package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.user.CardCheckingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CardCheckingUser, Long> {

    Optional<CardCheckingUser> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
