package com.sonia.java.bankcheckapplication.model.user;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authorities")
public class CardCheckingUserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private KnownAuthority value;

    @ManyToMany(mappedBy = "authorities")
    private List<CardCheckingUser> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public KnownAuthority getValue() {
        return value;
    }

    public void setValue(KnownAuthority value) {
        this.value = value;
    }

    public List<CardCheckingUser> getUsers() {
        return users;
    }

    public void setUsers(List<CardCheckingUser> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardCheckingUserAuthority that = (CardCheckingUserAuthority) o;
        return value.equals(that.value) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, users);
    }
}
