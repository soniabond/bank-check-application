package com.sonia.java.bankcheckapplication.model.bank.category;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DischargeEntity>  discharges = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<UserCategoryLimit> limits = new ArrayList<>();

    public Category() {

    }
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<UserCategoryLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<UserCategoryLimit> limits) {
        this.limits = limits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DischargeEntity> getDischarges() {
        return discharges;
    }

    public void setDischarges(Set<DischargeEntity> discharges) {
        this.discharges = discharges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", discharges=" + discharges +
                '}';
    }
}
