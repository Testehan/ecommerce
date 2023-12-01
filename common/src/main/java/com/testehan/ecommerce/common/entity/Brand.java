package com.testehan.ecommerce.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 1000 , nullable = false)
    private String logo;

    @ManyToMany
    @JoinTable(name = "brands_categories",
               joinColumns = @JoinColumn(name="brand_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    public Brand(String name, String logo, Set<Category> categories) {
        this.name = name;
        this.logo = logo;
        this.categories = categories;
    }

    @Transient
    public String getLogoPath() {
        if (this.id == null  ){
            return "/images/default-category.png";
        } else {
            return "/brand-logos/" + this.id + "/" + this.logo;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand brand)) return false;
        return Objects.equals(getName(), brand.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
