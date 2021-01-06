package com.sonia.java.bankcheckapplication.service;


import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.user.CardCheckingUserAuthority;
import com.sonia.java.bankcheckapplication.model.user.CardChekingUser;
import com.sonia.java.bankcheckapplication.model.user.SaveRegularUserRequest;
import com.sonia.java.bankcheckapplication.model.user.UserResponse;
import com.sonia.java.bankcheckapplication.repository.UserAuthorityRepository;
import com.sonia.java.bankcheckapplication.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions.*;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sonia.java.bankcheckapplication.model.user.CardCheckingUserAuthority.KnownAuthority.USER_ROLE;


@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserAuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserAuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(SaveRegularUserRequest request){
        CardChekingUser user = new CardChekingUser();
        var authority = authorityRepository.getByValue(USER_ROLE)
                .orElseThrow(()-> CardCheckExceptions.authorityNotFound(USER_ROLE.name()));

        user.setAuthorities(Set.of(authority));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(Instant.now());

        userRepository.save(user);
        return UserResponse.fromUser(user);

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        CardChekingUser user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with mail " + email+" not found"));
        Set<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getValue().name()))
                .collect(Collectors.toSet());
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
