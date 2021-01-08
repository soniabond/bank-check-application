package com.sonia.java.bankcheckapplication.service;


import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.user.*;
import com.sonia.java.bankcheckapplication.model.user.auth.CardCheckingUserDetails;
import com.sonia.java.bankcheckapplication.repository.AuthorityRepository;
import com.sonia.java.bankcheckapplication.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public UserResponse create(SaveUserRequest request){
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getRegularUserAuthorities()));
    }

    private void validateUniqueFields(SaveUserRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw CardCheckExceptions.duplicateEmail(email);
        }
    }

    private CardChekingUser save(SaveUserRequest request, Map<KnownAuthority, CardCheckingUserAuthority> authorities) {
        var user = new CardChekingUser();
        user.getAuthorities().putAll(authorities);
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return user;
    }

    private Map<KnownAuthority, CardCheckingUserAuthority> getRegularUserAuthorities() {
        CardCheckingUserAuthority authority = authorityRepository
                .findByValue(KnownAuthority.ROLE_USER)
                .orElseThrow(() -> CardCheckExceptions.authorityNotFound(KnownAuthority.ROLE_USER.name()));
        Map<KnownAuthority, CardCheckingUserAuthority> authorities = new EnumMap<>(KnownAuthority.class);
        authorities.put(KnownAuthority.ROLE_USER, authority);
        return authorities;
    }


    @Transactional
    public UserResponse createAdmin(SaveUserRequest request) {
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getAdminAuthorities()));
    }



    @Transactional
    public void mergeAdmins(List<SaveUserRequest> requests) {
        if (requests.isEmpty()) return;
        Map<KnownAuthority, CardCheckingUserAuthority> authorities = getAdminAuthorities();
        for (SaveUserRequest request : requests) {
            String email = request.getEmail();
            String firstName = request.getFirstName();
            CardChekingUser user = userRepository.findByEmail(email).orElseGet(() -> {
                var newUser = new CardChekingUser();
                newUser.setCreatedAt(Instant.now());
                newUser.setEmail(email);
                return newUser;
            });
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.getAuthorities().putAll(authorities);
            userRepository.save(user);
        }
    }

    private Map<KnownAuthority, CardCheckingUserAuthority> getAdminAuthorities() {
        return authorityRepository.findAllByValueIn(AuthorityRepository.ADMIN_AUTHORITIES)
                .collect(Collectors.toMap(
                        CardCheckingUserAuthority::getValue,
                        Function.identity(),
                        (e1, e2) -> e2,
                        () -> new EnumMap<>(KnownAuthority.class)));
    }




    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        CardChekingUser user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with mail " + email+" not found"));

        return new CardCheckingUserDetails(user);
    }
}
