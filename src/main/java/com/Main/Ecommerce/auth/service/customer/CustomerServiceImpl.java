package com.Main.Ecommerce.auth.service.customer;
import com.Main.Ecommerce.auth.model.ResetPassword;
import com.Main.Ecommerce.auth.repository.ResetPasswordRepository;
import com.Main.Ecommerce.dto.request.OtpCheckerRequest;
import com.Main.Ecommerce.dto.request.ResetPasswordRequest;
import com.Main.Ecommerce.dto.request.SignupRequest;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.service.mailSender.MailSenderImpl;
import com.Main.Ecommerce.dto.response.Response;
import com.Main.Ecommerce.exceptions.exception.UserAlreadyExist;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService  {

    private final CustomerRepository customerRepository;
    private final MailSenderImpl mailSender;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ResetPasswordRepository resetPasswordRepository;


    @Override
    @Transactional
    public  Response signup(SignupRequest SignupRequest) {

        ///// find user by email
        Optional<Customer> findCustomer= customerRepository.findByEmail(SignupRequest.email());

        //// handle completely new user
        if(findCustomer.isEmpty()){

            String encodedPassword = CreateDecodePassword(SignupRequest.password());
            Customer customer = Customer.builder().password(encodedPassword).email(SignupRequest.email()).build();
            String token=createSixDigitOtpCode(customer);
            mailSender.sendMail(SignupRequest.email(), token);
            return new Response("رمز یکبار مصرف به آدرس ایمیل شما ارسال شد", null);
        }

        /// handle user that is already verified
        else if(findCustomer.get().isVerified()){
            throw new UserAlreadyExist("ایمیمل قبلا ثبت شده است");
        }

        ///  handle the user that is not new and not verified yet by otp
        String token=createSixDigitOtpCode(findCustomer.get());
        mailSender.sendMail(SignupRequest.email(), token);
        return new Response("رمز یکبار مصرف به آدرس ایمیل شما ارسال شد", null);
    }

    @Override
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



    ///  send opt as email in order to verify the new user email
    @Override
    public void CheckSignUPOtpCode(OtpCheckerRequest otpCheckerRequest){

        /// find customer by email
        Customer findByEmail = customerRepository.findByEmail(otpCheckerRequest.email()).orElseThrow(() -> new RuntimeException("ایمیمل معتبر نیست"));
        if(findByEmail.getExpiryTokenDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("اعتبار رمز یکبار مصرف به پایان رسید . دوباره درخواست دهید");
        }

        ///  check otp is expired
        else if (!findByEmail.getToken().equals(otpCheckerRequest.otp())){
            throw new RuntimeException("رمز اشتباه است");
        }

        /// verify the user and save the data
        findByEmail.setVerified(true);
        customerRepository.save(findByEmail);

    }

    @Override
    public Response login(SignupRequest signupRequest) {

        /// find customer by email
        Customer findCustomerByEmail = customerRepository.findByEmail(signupRequest.email()).
                orElseThrow(() -> new RuntimeException("ایمیل نا معتبر است"));

        if (!findCustomerByEmail.isVerified()) {
            throw new UserAlreadyExist("ثبت نام شما هنوز تکمیل نشده.  ابتدا ثبت نام کنید");
        }


        /// check if data is valid or not
        Authentication isAuthenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signupRequest.email(), signupRequest.password()));
        if (isAuthenticated.isAuthenticated()) {
            return new Response("ورود با موفقیت انجام شد", null);
        }

        return null;
    }



    /// ///******** this is  reset password section************/////////

    @Override
    public void initiatePasswordReset(String email) {

        ///  find the user by email
        Customer customer=customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("ایمیمل موجود نیست"));

        ///  find unverified customer
        if(!customer.isVerified()){
            throw new RuntimeException("ایمیمل معتبر نیست");
        }

        ///  create the reset password token
        String token= createResetPasswordToken(customer);
        mailSender.sendMail(customer.getEmail(), token);

    }

    @Override
    public void verifyPasswordResetOtp(OtpCheckerRequest otpCheckerRequest) {

        ///  find Reset password that is related to user
        ResetPassword resetPassword = resetPasswordRepository.findByTokenAndCustomerEmail(otpCheckerRequest.otp(), otpCheckerRequest.email())
                .orElseThrow(()->new RuntimeException("رمز اشتباه است "));

        /// find expired password
        if(resetPassword.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("اعتبار توکن تمام شد  دوباره در خواست بدهید");
        }

        ///  delete the reset password because he is verified now
        Customer customer=resetPassword.getCustomer();
        customer.setResetPassword(null);
        customerRepository.save(customer);

    }


    @Override
    public void resetPassword(ResetPasswordRequest request) {

        ///  find the user by email
        Customer findCustomer=customerRepository.findByEmail(request.email()).orElseThrow(() ->  new RuntimeException("ایمیمل موجود نیست"));

        ///  find if password and confirm password are same or not
        if(!request.password().equals(request.confirmPassword())){
            throw new RuntimeException("پسورد و تاییدیه یکسان نیست");
        }

        ///  hash and create the new password for user
        String hashPassword = CreateDecodePassword(request.password());
        findCustomer.setPassword(hashPassword);
        customerRepository.save(findCustomer);
    }


    @Override
    public String  createResetPasswordToken(Customer customer) {
        Random random = new SecureRandom();
        String token = String.valueOf(random.nextInt(900000) + 100000);
        ResetPassword resetPassword=ResetPassword.builder().customer(customer).token(token).expirationDate(LocalDateTime.now().plusMinutes(2L)).build();
        customer.setResetPassword(resetPassword);
        resetPasswordRepository.save(resetPassword);
        customerRepository.save(customer);
        return token;
    }
}
