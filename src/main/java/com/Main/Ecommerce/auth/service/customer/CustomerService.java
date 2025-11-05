package com.Main.Ecommerce.auth.service.customer;
import com.Main.Ecommerce.dto.request.SignupRequest;
import com.Main.Ecommerce.dto.response.Response;

public interface CustomerService {

     Response signup(SignupRequest email);
     Response login(SignupRequest email);
     void CheckSignUPOtpCode(String otp, String email);
}
