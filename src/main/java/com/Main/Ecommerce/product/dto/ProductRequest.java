package com.Main.Ecommerce.product.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductRequest(@NotBlank(message = "نام محصول الزامی است") String name, String description,
                             @NotNull(message = "قیمت را وارد کنید") @DecimalMin(value = "0.0", message = "قیمت باید عددی مثبت باشد") BigDecimal price,
                             @NotNull(message = "تعداد را وارد کنید") @Min(value = 0, message = "تعداد حداقل باید صفر یا بیشتر باشد") Integer stock,
                             @NotNull(message = "وضعیت فعال یا غیر فعال را مشخص کنید") Boolean isActive , @NotNull(message = "دسته‌بندی را انتخاب کنید") Long categoryId) {}


