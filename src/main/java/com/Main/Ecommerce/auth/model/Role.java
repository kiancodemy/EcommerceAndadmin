package com.Main.Ecommerce.auth.model;

import com.Main.Ecommerce.auth.enums.EnumRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Date created;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private EnumRole role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Customer> customers = new HashSet<>();

    // Automatically set creation date
    @PrePersist
    protected void onCreate() {
        this.created = new Date();
    }


}
