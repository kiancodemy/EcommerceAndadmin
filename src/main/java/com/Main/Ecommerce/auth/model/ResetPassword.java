package com.Main.Ecommerce.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String token;
    private Date expirationDate;

    @OneToOne
    @JoinColumn(name="customer-id",referencedColumnName = "id", nullable = false)
    private Customer customer;
}
