package com.Main.Ecommerce.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record SignupRequest(@NotBlank(message = "ایمیل نباید خالی باشد")
                           @Email(message = "لطفا ایمیل معتبر وارد کنید") String email,
                            @NotBlank(message = "پسورد نباید خالی باشد")
                           @Size(min = 5, message = "طول پسورد نباید کمتر از 5 باشد") String password) {

}
