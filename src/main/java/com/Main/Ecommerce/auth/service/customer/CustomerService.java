package com.Main.Ecommerce.auth.service.customer;

import com.Main.Ecommerce.auth.model.Customer;

public interface CustomerService {

    Customer signup(String email);
    Customer login(String email);
    void submitOtp(String email);

}
