package com.Main.Ecommerce.auth.repository;

import com.Main.Ecommerce.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
