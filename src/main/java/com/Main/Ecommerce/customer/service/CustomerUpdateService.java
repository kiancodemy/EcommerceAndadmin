package com.Main.Ecommerce.customer.service;

import com.Main.Ecommerce.auth.enums.EnumRole;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.model.Role;
import com.Main.Ecommerce.customer.dto.CustomerUpdateRequest;

import java.util.List;

public interface CustomerUpdateService {

    Customer updateCustomer(String email,CustomerUpdateRequest request);

    Role createRole(EnumRole roleName);

    Customer addRoleToCustomer(Long customerId, Long roleId);
    Customer removeRoleFromCustomer(Long customerId, Long roleId);


    List<Customer> allCustomers();
}
