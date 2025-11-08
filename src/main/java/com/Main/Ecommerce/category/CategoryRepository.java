package com.Main.Ecommerce.category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    /// // tested
    Optional<Category> findByName(String name);



}
