package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.user.CardCheckingUserAuthority;
import com.sonia.java.bankcheckapplication.model.user.KnownAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<CardCheckingUserAuthority, Integer> {

    Optional<CardCheckingUserAuthority> findByValue(KnownAuthority value);
}
