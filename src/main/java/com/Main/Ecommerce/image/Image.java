package com.Main.Ecommerce.image;

import com.Main.Ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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
