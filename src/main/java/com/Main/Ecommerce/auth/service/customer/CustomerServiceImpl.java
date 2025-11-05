package com.Main.Ecommerce.auth.service.customer;
import com.Main.Ecommerce.dto.request.EmailSenderRequest;
import com.Main.Ecommerce.dto.request.SignupRequest;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.service.mailSender.MailSenderImpl;
import com.Main.Ecommerce.dto.response.Response;
import com.Main.Ecommerce.exceptions.exception.UserAlreadyExist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService  {

    private final CustomerRepository customerRepository;
    private final MailSenderImpl mailSender;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public  Response signup(SignupRequest SignupRequest) {
        Optional<Customer> findCustomer= customerRepository.findByEmail(SignupRequest.email());

        if(findCustomer.isEmpty()){

            String encodedPassword = CreateDecodePassword(SignupRequest.password());
             Customer customer = Customer.builder().password(encodedPassword).email(SignupRequest.email()).build();
            String token=createSixDigitOtpCode(customer);
            mailSender.sendMail(EmailSenderRequest.builder().token(token).email(SignupRequest.email()).build());
            return new Response("رمز یکبار مصرف به آدرس ایمیل شما ارسال شد", null);
        }

        else if(findCustomer.get().isVerified()){
            throw new UserAlreadyExist("ایمیمل قبلا ثبت شده است");
        }

        String token=createSixDigitOtpCode(findCustomer.get());
        mailSender.sendMail(EmailSenderRequest.builder().token(token).email(SignupRequest.email()).build());
        return new Response("رمز یکبار مصرف به آدرس ایمیل شما ارسال شد", null);
    }



    public String createSixDigitOtpCode(Customer customer) {
        SecureRandom random = new SecureRandom();
        String token = String.valueOf(random.nextInt(900000) + 100000);
        customer.setToken(token);
        customer.setExpiryTokenDate(LocalDateTime.now().plusMinutes(2L));
        customerRepository.save(customer);
        return token;
    }

    public String CreateDecodePassword(String password){
        return passwordEncoder.encode(password);

    }

    @Override
    public void CheckSignUPOtpCode(String otp, String email){
        Customer findByEmail = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("ایمیمل معتبر نیست"));
        if(findByEmail.getExpiryTokenDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("token is expired");
        }
        else if (!findByEmail.getToken().equals(otp)){
            throw new RuntimeException("opt is not valid");
        }

        else if(findByEmail.getExpiryTokenDate().isAfter(LocalDateTime.now())){
            findByEmail.setVerified(true);
            customerRepository.save(findByEmail);
        }
    }

    @Override
    public Response login(SignupRequest signupRequest) {
        Customer findCustomerByEmail = customerRepository.findByEmail(signupRequest.email()).
                orElseThrow(() -> new RuntimeException("ایمیل نا معتبر است"));

        Authentication isAuthenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(findCustomerByEmail.getEmail(), findCustomerByEmail.getPassword()));

        if (isAuthenticated.isAuthenticated()) {
            return new Response("ورود با موفقیت انحام شد", null);
        }

        return null;
    }




}
