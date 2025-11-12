package com.Main.Ecommerce.customer.dto;

import com.Main.Ecommerce.customer.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerUpdateRequest(String name, @NotBlank(message = "ایمیل خالی نباشد") @Email(message = "ایمیل معتبر نیست") String email, Gender gender) {
}
