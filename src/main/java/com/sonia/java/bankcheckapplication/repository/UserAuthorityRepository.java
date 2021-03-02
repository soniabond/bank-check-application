package com.sonia.java.bankcheckapplication.repository;

import com.sonia.java.bankcheckapplication.model.user.CardCheckingUserAuthority;
import com.sonia.java.bankcheckapplication.model.user.KnownAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface UserAuthorityRepository extends JpaRepository<CardCheckingUserAuthority, Integer> {


    Set<KnownAuthority> ADMIN_AUTHORITIES = EnumSet.of(KnownAuthority.ROLE_USER, KnownAuthority.ROLE_ADMIN);

    Optional<CardCheckingUserAuthority> findByValue(KnownAuthority value);

    Stream<CardCheckingUserAuthority> findAllByValueIn(Set<KnownAuthority> values);
}
