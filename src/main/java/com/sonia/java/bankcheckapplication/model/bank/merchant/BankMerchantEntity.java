package com.sonia.java.bankcheckapplication.model.bank.merchant;

import com.sonia.java.bankcheckapplication.model.bank.Bank;
import com.sonia.java.bankcheckapplication.model.bank.category.Category;
import com.sonia.java.bankcheckapplication.model.user.CardChekingUser;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

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
    private CardChekingUser user;

    public CardChekingUser getUser() {
        return user;
    }

    public void setUser(CardChekingUser user) {
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

