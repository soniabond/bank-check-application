package com.sonia.java.bankcheckapplication.model.bank.category;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "discharge")
public class DischargeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public DischargeEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DischargeEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DischargeEntity that = (DischargeEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "DischargeEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
