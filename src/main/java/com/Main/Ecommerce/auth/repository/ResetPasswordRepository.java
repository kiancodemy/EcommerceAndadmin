package com.Main.Ecommerce.auth.repository;

import com.Main.Ecommerce.auth.model.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {
    Optional<ResetPassword> findByTokenAndCustomerEmail(String token, String customerEmail);

}

