package com.Main.Ecommerce.product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
    SELECT DISTINCT p FROM Product p
    LEFT JOIN FETCH p.comments
    LEFT JOIN FETCH p.images
    """)
    List<Product> findAllWithImagesCommentsCategory();




}
