package com.Main.Ecommerce.auth.service.customer;
import com.Main.Ecommerce.auth.dto.request.OtpCheckerRequest;
import com.Main.Ecommerce.auth.dto.request.ResetPasswordRequest;
import com.Main.Ecommerce.auth.dto.request.SignupRequest;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.auth.model.Customer;


public interface CustomerService {

     Response signup(SignupRequest email);
     Response login(SignupRequest email);

     String createSixDigitOtpCode(Customer customer);

     void CheckSignUPOtpCode(OtpCheckerRequest otpCheckerRequest);

     void initiatePasswordReset(String email);

     void verifyPasswordResetOtp(OtpCheckerRequest otpCheckerRequest);

     void resetPassword(ResetPasswordRequest request);

     String createResetPasswordToken(Customer customer);
}
