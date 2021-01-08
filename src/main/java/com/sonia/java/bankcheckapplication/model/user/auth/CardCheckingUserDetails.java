package com.sonia.java.bankcheckapplication.model.user.auth;

import com.sonia.java.bankcheckapplication.model.user.CardChekingUser;
import org.springframework.security.core.userdetails.User;

import java.util.EnumSet;

public class CardCheckingUserDetails extends User {

    public CardCheckingUserDetails(CardChekingUser user) {
        super(user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                EnumSet.copyOf(user.getAuthorities().keySet()));
    }
}
