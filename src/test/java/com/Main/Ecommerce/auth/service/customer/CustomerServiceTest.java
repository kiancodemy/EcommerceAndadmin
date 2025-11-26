package com.Main.Ecommerce.auth.service.customer;
import com.Main.Ecommerce.auth.configurations.JWT.JwtUtils;
import com.Main.Ecommerce.auth.dto.request.OtpCheckerRequest;
import com.Main.Ecommerce.auth.dto.request.ResetPasswordRequest;
import com.Main.Ecommerce.auth.dto.request.SignupRequest;
import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.model.ResetPassword;
import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.repository.ResetPasswordRepository;
import com.Main.Ecommerce.auth.service.mailSender.MailSenderImpl;
import static org.assertj.core.api.Assertions.*;
import com.Main.Ecommerce.exceptions.exception.UserAlreadyExist;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
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

    @MockitoBean
    private JwtUtils jwtUtils;


    @Autowired
    private CustomerServiceImpl customerServiceImpl;


    @Test
    void it_Should_Not_Signup_VerifiedCustomer() {
        ///given
        SignupRequest request = new SignupRequest("a@gmail.com", "123");
        Customer customer = Customer.builder().isVerified(true).build();
        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.of(customer));

        /// when + then
        assertThatThrownBy(() ->customerServiceImpl.signup(request))
                .isInstanceOf(UserAlreadyExist.class)
                .hasMessage("ایمیمل قبلا ثبت شده است");

    }
    @Test
    void it_Should_Signup_if_EMPTY() {
        ///given
        ArgumentCaptor<String> email = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> otp = ArgumentCaptor.forClass(String.class);
        SignupRequest request = new SignupRequest("a@gmail.com", "123");
        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.empty()
                );

        /// when + then
        Response response = customerServiceImpl.signup(request);
        then(mailSender).should().sendMail(email.capture(), otp.capture());
        String capturedEmail = email.getValue();
        String capturedToken = otp.getValue();

        assertThat(capturedEmail).isEqualTo("a@gmail.com");
        assertThat(capturedToken).hasSize(6);
        assertThat(response).isNotNull();
        assertThat(response.message()).isEqualTo("رمز یکبار مصرف به آدرس ایمیل شما ارسال شد");
        assertThat(response.data()).isNull();
    }

    @Test
    void it_should_SignUp_Otp_Resender(){

        /// given
        ArgumentCaptor<String> email = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> token = ArgumentCaptor.forClass(String.class);
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(Customer.builder().isVerified(false).build()));

        /// when
        customerServiceImpl.SignupOtpResender("kian@gmail.com");

        /// then
        then(mailSender).should().sendMail(email.capture(), token.capture());
        String capturedEmail = email.getValue();
        String capturedToken = token.getValue();
        assertThat(capturedToken).hasSize(6);
        assertThat(capturedEmail).isEqualTo("kian@gmail.com");

    }
    @Test
    void it_Should_create_SixDigit_OtpCode() {

        ///  given
        Customer customer = Customer.builder().email("a@GMAil.com").build();
        ArgumentCaptor<Customer> customerCapture=ArgumentCaptor.forClass(Customer.class);

        /// when

        String token=customerServiceImpl.createSixDigitOtpCode(customer);

        /// then
        then(customerRepository).should().save(customerCapture.capture());
        Customer savedCustomer=customerCapture.getValue();
        assertThat(token).isGreaterThan("10000");
        assertThat(savedCustomer.getEmail()).isEqualTo("a@GMAil.com");
        assertThat(savedCustomer.getToken()).hasSize(6);

    }


    @Test
    void it_should_Create_Decode_Password(){

        /// given
        given(passwordEncoder.encode(anyString())).willReturn("123");

        /// when
        String token = customerServiceImpl.CreateDecodePassword("sd");
        assertThat(token).isEqualTo("123");
        assertThat(token).isNotNull();

    }

    @Test
    public void it_Should_Check_SignUp_OtpCode_when_expired(){

        /// given
        Customer customer = Customer.builder().token("321").expiryTokenDate(LocalDateTime.now().minusMinutes(2L)).build();
        OtpCheckerRequest otpCheckerRequest = new OtpCheckerRequest("a@gmail.com", "123");
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));

        /// when +then
        assertThatThrownBy(()->customerServiceImpl.CheckSignUPOtpCode(otpCheckerRequest)).isInstanceOf(RuntimeException.class).hasMessage("اعتبار رمز یکبار مصرف به پایان رسید . دوباره درخواست دهید");

    }
    @Test
    public void it_Should_Check_SignUp_OtpCode_when_Token_Is_Wrong(){

        /// given
        Customer customer = Customer.builder().token("321").expiryTokenDate(LocalDateTime.now().plusMinutes(2L)).build();
        OtpCheckerRequest otpCheckerRequest = new OtpCheckerRequest("a@gmail.com", "123");
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));

        /// when +then
        assertThatThrownBy(() -> customerServiceImpl.CheckSignUPOtpCode(otpCheckerRequest)).isInstanceOf(RuntimeException.class).hasMessage("رمز اشتباه است");

    }

    @Test
    public void it_Should_Check_SignUp_OtpCode_when_everything_Is_Ok() {

        /// given
        ArgumentCaptor<Customer> customerArgumentCaptor=ArgumentCaptor.forClass(Customer.class);
        Customer customer = Customer.builder().token("123").expiryTokenDate(LocalDateTime.now().plusMinutes(2L)).build();
        OtpCheckerRequest otpCheckerRequest = new OtpCheckerRequest("a@gmail.com", "123");
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));

        /// when
       customerServiceImpl.CheckSignUPOtpCode(otpCheckerRequest);

       /// then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer savedCustomer=customerArgumentCaptor.getValue();
        assertThat(savedCustomer.isVerified()).isTrue();

    }

    @Test
    public void it_should_login_successfully() {

        /// given
        SignupRequest signupRequest = new SignupRequest("a@gmail", "123");
        Customer customer = Customer.builder().email("a@gmail.com").isVerified(true).build();
        given(authenticationManager.authenticate(any(Authentication.class))).willReturn(new UsernamePasswordAuthenticationToken(customer, null, customer.getAuthorities()));
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));

        /// when
        Response response=customerServiceImpl.login(signupRequest);
        assertThat(response).isNotNull();
        assertThat(response.message()).isEqualTo("ورود با موفقیت انجام شد");


    }

    @Test
    public void it_Should_create_ResetPassword_Token(){

        /// given
        Customer customer=Customer.builder().build();
        String token=customerServiceImpl.createResetPasswordToken(customer);

        /// when
        then(resetPasswordRepository).should().save(any(ResetPassword.class));
        then(customerRepository).should().save(any(Customer.class));
        assertThat(token).isNotNull();
        assertThat(token).hasSize(6);


    }

    @Test
    void it_Should_InitiatePasswordReset() {

        ///given
        Customer customer = Customer.builder().email("a").isVerified(true).build();
        ArgumentCaptor<String> email=ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> token=ArgumentCaptor.forClass(String.class);

        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));
        /// when
        customerServiceImpl.initiatePasswordReset("a");

        /// then
        then(mailSender).should().sendMail(email.capture(), token.capture());
        String emailCapture=email.getValue();
        String tokenCapture=token.getValue();
        assertThat(emailCapture).isEqualTo(customer.getEmail());
        assertThat(tokenCapture).hasSize(6);
    }

    @Test
    void it_Should_VerifyPasswordResetOtp_while_Password_isIncorrect() {

        ///given
        given(resetPasswordRepository.findByTokenAndCustomerEmail(anyString(), anyString())).willReturn(Optional.empty());

        /// then
        assertThatThrownBy(()-> customerServiceImpl.verifyPasswordResetOtp(new OtpCheckerRequest("a", "a"))).isInstanceOf(RuntimeException.class).hasMessage("رمز اشتباه است ");
    }

    @Test
    void it_Should_VerifyPasswordResetOtp_while_Token_isExpired() {

        ///given
        ResetPassword resetPassword = ResetPassword.builder().expirationDate(LocalDateTime.now().minusMinutes(2L)).build();
        given(resetPasswordRepository.findByTokenAndCustomerEmail(anyString(), anyString())).willReturn(Optional.of(resetPassword));


        /// then
        assertThatThrownBy(()-> customerServiceImpl.verifyPasswordResetOtp(new OtpCheckerRequest("a", "a"))).isInstanceOf(RuntimeException.class).hasMessage("اعتبار توکن تمام شد  دوباره در خواست بدهید");
    }
    @Test
    void it_Should_VerifyPasswordResetOtp_while_EveryThing_Ok() {

        ///given
        ArgumentCaptor<Customer> customerArgumentCaptor=ArgumentCaptor.forClass(Customer.class);
        ResetPassword resetPassword = ResetPassword.builder().customer(Customer.builder().build()).expirationDate(LocalDateTime.now().plusMinutes(2L)).build();
        given(resetPasswordRepository.findByTokenAndCustomerEmail(anyString(), anyString())).willReturn(Optional.of(resetPassword));

        /// when
        customerServiceImpl.verifyPasswordResetOtp(new OtpCheckerRequest("a", "a"));

        /// then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customer=customerArgumentCaptor.getValue();
        assertThat(customer.getResetPassword()).isNull();

    }


    @Test
    void it_Should_ResetPassword_when_Password_and_Confirm_NotSame() {

        ///given
        ResetPasswordRequest request = new ResetPasswordRequest("a", "a", "b");
        /// when
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(mock(Customer.class)));

        /// then
        assertThatThrownBy(()-> customerServiceImpl.resetPassword(request)).hasMessage("پسورد و تاییدیه یکسان نیست");
    }
    @Test
    void it_Should_ResetPassword_when_ok() {

        ///given
        Customer customer = Customer.builder().password("123").build();
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        ResetPasswordRequest request = new ResetPasswordRequest("a", "a", "a");
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));

        /// when
        customerServiceImpl.resetPassword(request);
        /// then
        then(customerRepository).should().save(argumentCaptor.capture());
        Customer customer1=argumentCaptor.getValue();
        assertThat(customer1.getPassword()
        ).isNotEqualTo("1234");

    }
}