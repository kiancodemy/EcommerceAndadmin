package com.Main.Ecommerce.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(@NotBlank(message = "ایمیل نباید خالی باشد")String email , @NotBlank(message = "پسورد نباید خالی باشد")String password , @NotBlank(message = "پسورد تاییدیه نباید خالی باشد")String confirmPassword ) {
}
