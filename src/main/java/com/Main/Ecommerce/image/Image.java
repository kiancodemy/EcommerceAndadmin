package com.Main.Ecommerce.image;

import com.Main.Ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String downloadedUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product-id")
    private Product product;




}
