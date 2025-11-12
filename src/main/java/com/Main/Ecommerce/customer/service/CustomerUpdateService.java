package com.Main.Ecommerce.customer.service;

import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.customer.dto.CustomerUpdateRequest;

public interface CustomerUpdateService {

    Customer updateCustomer(String email,CustomerUpdateRequest request);

    Customer addRoleToCustomer(String email, Long roleId);
    Customer removeRoleFromCustomer(String email, Long roleId);


}
