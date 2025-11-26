package com.Main.Ecommerce.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CustomerPageDto {
    List<CustomerDto> content;
    long totalElements;
    int totalPages;
    int page;
    int size;
}
