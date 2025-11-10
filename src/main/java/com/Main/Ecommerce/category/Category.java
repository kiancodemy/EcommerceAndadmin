package com.Main.Ecommerce.category;
import com.Main.Ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Product> products=new HashSet<>();


    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();

    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }


    /// remove product
    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }



}
