package com.Main.Ecommerce.auth.service.customer;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.dto.request.OtpCheckerRequest;
import com.Main.Ecommerce.dto.request.ResetPasswordRequest;
import com.Main.Ecommerce.dto.request.SignupRequest;
import com.Main.Ecommerce.dto.response.Response;

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
