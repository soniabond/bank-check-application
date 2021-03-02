package com.sonia.java.bankcheckapplication.model.user;

import com.sonia.java.bankcheckapplication.model.bank.category.UserCategoryLimit;
import com.sonia.java.bankcheckapplication.model.bank.merchant.BankMerchantEntity;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "users")
public class CardCheckingUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, name = "email", unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    @ManyToMany
    @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKey(name = "value")
    private Map<KnownAuthority, CardCheckingUserAuthority> authorities = new EnumMap<>(KnownAuthority.class);


    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<BankMerchantEntity>  merchants = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserCategoryLimit> limits = new HashSet<>();

    public Set<UserCategoryLimit> getLimits() {
        return limits;
    }

    public void setLimits(Set<UserCategoryLimit> limits) {
        this.limits = limits;
    }

    public Set<BankMerchantEntity> getMerchants() {
        return merchants;
    }

    public void setMerchants(Set<BankMerchantEntity> merchants) {
        this.merchants = merchants;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Map<KnownAuthority, CardCheckingUserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Map<KnownAuthority, CardCheckingUserAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardCheckingUser user = (CardCheckingUser) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "CardChekingUser{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
