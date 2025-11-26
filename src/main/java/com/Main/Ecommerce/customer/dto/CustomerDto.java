package com.Main.Ecommerce.customer.dto;
import com.Main.Ecommerce.auth.dto.response.RoleDto;
import com.Main.Ecommerce.customer.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class CustomerDto {

    private Long id;

    private String name;

    private Gender gender;

    private String email;

    private Set<RoleDto> roles;
}
