package com.Main.Ecommerce.auth.repository;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.customer.filter.CustomerSearchFilter;
import com.Main.Ecommerce.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.roles WHERE c.email = :email")
    Optional<Customer> findByEmail(@Param("email") String email);

}



