package com.Main.Ecommerce.auth.model;
import com.Main.Ecommerce.customer.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Builder.Default
    private boolean isVerified=false;

    private String token;

    private LocalDateTime expiryTokenDate;

    @NotBlank(message = "email should not be null")
    @Email(message = "email is not valid")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "پسورد نباید خالی باشد")
    @Size(min = 5, message = "طول پسورد نباید کمتر از 5 باشد")
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "customer", orphanRemoval = true, cascade = CascadeType.ALL)
    private ResetPassword resetPassword;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name())).toList();

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    /// Add role and maintain bidirectional relationship
    public void addRole(Role role) {
        this.roles.add(role);
        role.getCustomers().add(this);
    }

    /// Remove role and maintain bidirectional relationship
    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getCustomers().remove(this);
    }
}
