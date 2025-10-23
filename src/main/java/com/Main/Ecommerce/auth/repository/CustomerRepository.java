package com.Main.Ecommerce.auth.repository;
import com.Main.Ecommerce.auth.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM Customer WHERE email= :email ", nativeQuery = true)
    Optional<Customer> findByEmail(@Param("email") String email);




}





