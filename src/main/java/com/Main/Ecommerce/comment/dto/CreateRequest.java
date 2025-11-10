package com.Main.Ecommerce.comment.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRequest(
        @NotNull(message = "همه فیلد ها را پر کنید") String text) {
}
