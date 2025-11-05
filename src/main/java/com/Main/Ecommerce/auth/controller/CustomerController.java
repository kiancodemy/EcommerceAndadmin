package com.Main.Ecommerce.auth.controller;
import com.Main.Ecommerce.auth.service.customer.CustomerServiceImpl;
import com.Main.Ecommerce.auth.service.mailSender.MailSenderImpl;
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
    private final MailSenderImpl mailSender;

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

}
