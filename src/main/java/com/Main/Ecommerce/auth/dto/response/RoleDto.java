package com.Main.Ecommerce.auth.dto.response;
import com.Main.Ecommerce.auth.enums.EnumRole;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class RoleDto {

    private Long id;

    private EnumRole role;
}
