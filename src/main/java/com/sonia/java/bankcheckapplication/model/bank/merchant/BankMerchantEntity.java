package com.sonia.java.bankcheckapplication.model.bank.merchant;

import com.sonia.java.bankcheckapplication.model.bank.Bank;
import com.sonia.java.bankcheckapplication.model.user.CardCheckingUser;

import javax.persistence.*;

@Entity
@Table(name = "merchants")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BD_TYPE")
public abstract class BankMerchantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private CardCheckingUser user;

    public CardCheckingUser getUser() {
        return user;
    }

    public void setUser(CardCheckingUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract Bank getBank();









}

