package com.Main.Ecommerce.auth.service.customer;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.repository.ResetPasswordRepository;
import com.Main.Ecommerce.auth.service.mailSender.MailSenderImpl;
import com.Main.Ecommerce.dto.request.SignupRequest;

import static org.assertj.core.api.Assertions.*;

import com.Main.Ecommerce.dto.response.Response;
import com.Main.Ecommerce.exceptions.exception.UserAlreadyExist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class CustomerServiceTest {

    @MockitoBean
    private CustomerRepository customerRepository;

    @MockitoBean
    private MailSenderImpl mailSender;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private CustomerService customerService;




    @Test
    void it_Should_Not_Signup_VerifiedCustomer() {
        ///given
        SignupRequest request = new SignupRequest("a@gmail.com", "123");
        Customer customer = Customer.builder().isVerified(true).build();
        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.of(customer));

        /// when + then
        assertThatThrownBy(() -> customerService.signup(request))
                .isInstanceOf(UserAlreadyExist.class)
                .hasMessage("ایمیمل قبلا ثبت شده است");



    }
    @Test
    void it_Should_Signup_if_EMPTY() {
        ///given
        SignupRequest request = new SignupRequest("a@gmail.com", "123");

        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.empty()
                );

        /// when + then
        Response response = customerService.signup(request);
        assertThat(response).isNotNull();
        assertThat(response.message()).isEqualTo("رمز یکبار مصرف به آدرس ایمیل شما ارسال شد");
        assertThat(response.data()).isNull();


    }


    @Test
    void it_Should_create_SixDigit_OtpCode() {
        Customer customer=Mockito.mock(Customer.class);
        String token=customerService.createSixDigitOtpCode(customer);
        assertThat(token).isNotNull();
        assertThat(token).hasSize(6);
    }

}