package com.Main.Ecommerce.auth.controller;
import com.Main.Ecommerce.auth.service.customer.CustomerServiceImpl;
import com.Main.Ecommerce.dto.request.OtpCheckerRequest;
import com.Main.Ecommerce.dto.request.ResetPasswordRequest;
import com.Main.Ecommerce.dto.request.SignupRequest;
import com.Main.Ecommerce.dto.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<Response> SignUp(@Valid @RequestBody SignupRequest emailRequest) {
        Response SignupResponse = customerServiceImpl.signup(emailRequest);
        return ResponseEntity.ok().body(SignupResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<Response> Login(@Valid @RequestBody SignupRequest signupRequest) {
        Response LoginResponse = customerServiceImpl.login(signupRequest);
        return ResponseEntity.ok().body(LoginResponse);

    }

    ///  SENDING OTP FOR NEW USER IN ORDER TO CHECK IF HIS EMAIL IS VALID OR NOT !!!!
    @PostMapping("/checkotp")
    public  ResponseEntity<Response> checkSignupOtp(@Valid @RequestBody OtpCheckerRequest otpCheckerRequest) {
        customerServiceImpl.CheckSignUPOtpCode(otpCheckerRequest);
        return ResponseEntity.ok().body(new Response("رمز یکبار مصرف معتبر بود", null));

    }

    ////****** ResetPassword (forget password) section ********* /////

    @PostMapping("/initialResetPassword/{email}")
    public ResponseEntity<Response> initialResetPassword(@PathVariable("email") String email) {

        customerServiceImpl.initiatePasswordReset(email);
        return ResponseEntity.ok().body(new Response("رمز یکبار مصرف به ایمیل شما ارسال شذ", null));
    }

    @PostMapping("/OtpCheckerRequest")
    public ResponseEntity<Response> OtpCheckerRequest(@Valid @RequestBody OtpCheckerRequest otpCheckerRequest) {

        customerServiceImpl.verifyPasswordResetOtp(otpCheckerRequest);
        return ResponseEntity.ok().body(new Response("رمز یکبار مصرف تایید شد", null));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {

        customerServiceImpl.resetPassword(request);
        return ResponseEntity.ok().body(new Response("رمز شما عوض شد", null));
    }

}
