package com.sonia.java.bankcheckapplication.service;


import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.user.*;
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

import java.time.Instant;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sonia.java.bankcheckapplication.model.user.KnownAuthority.ROLE_USER;



@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserAuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserAuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(SaveUserRequest request){
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getRegularUserAuthorities()));
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        CardChekingUser user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User with mail " + email+" not found"));
        Set<GrantedAuthority> grantedAuthorities = user.getAuthorities().keySet().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    private void validateUniqueFields(SaveUserRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            //throw FileSharingExceptions.duplicateEmail(email);
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



}
