package com.Main.Ecommerce.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OtpCheckerRequest(@NotBlank(message = "ایمیل نباید خالی باشد")String email, @NotBlank(message = "رمز یکبار مصرف نباید خالی باشد") String otp) {
}
