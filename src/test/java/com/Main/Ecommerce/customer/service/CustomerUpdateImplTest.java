package com.Main.Ecommerce.customer.service;

import com.Main.Ecommerce.auth.enums.EnumRole;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.model.Role;
import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.repository.RoleRepository;
import com.Main.Ecommerce.auth.service.customer.CustomerService;
import com.Main.Ecommerce.customer.dto.CustomerUpdateRequest;
import com.Main.Ecommerce.customer.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
class CustomerUpdateImplTest {
    @MockitoBean
    private CustomerRepository customerRepository;

    @MockitoBean
    private  RoleRepository roleRepository;

    @Autowired
    private CustomerUpdateImpl customerUpdate;

    Customer customer;
    Role role;

    @BeforeEach
    public void setup() {
        customer=Customer.builder().email("a").name("b").build();
        role=Role.builder().role(EnumRole.ADMIN).build();

    }

    @Test
    void it_Should_UpdateCustomer() {

        ///given
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("B", "B", Gender.FEMALE);
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));

        /// when
        customerUpdate.updateCustomer("Ass",customerUpdateRequest);

        /// then
        then(customerRepository).should().save(argumentCaptor.capture());
        Customer customerValue=argumentCaptor.getValue();
        assertThat(customerValue).satisfies(c -> {
            assertThat(c.getEmail()).isEqualTo("B");
            assertThat(customerValue.getGender()).isEqualTo(Gender.FEMALE);
            assertThat(customerValue.getName()).isEqualTo("B");
        });
    }

    @Test
    void it_Should_AddRoleToCustomer() {

        ///given
        ArgumentCaptor<Customer> captor=ArgumentCaptor.forClass(Customer.class);
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(customer));
        given(roleRepository.findById(anyLong())).willReturn(Optional.of(role));

        /// when
        customerUpdate.addRoleToCustomer("a", 2L);

        /// then
        then(customerRepository).should().save(captor.capture());
        Customer customer1=captor.getValue();
        assertThat(customer1.getRoles()).hasSize(1);


    }

    @Test
    void it_Should_RemoveRoleFromCustomer() {

        ///given
        ArgumentCaptor<Customer> captor=ArgumentCaptor.forClass(Customer.class);
        given(customerRepository.findByEmail(anyString())).willReturn(Optional.of(Customer.builder().roles(new HashSet<>(Set.of(role))).build()));
        given(roleRepository.findById(anyLong())).willReturn(Optional.of(role));

        /// when
        customerUpdate.removeRoleFromCustomer("a", 2L);

        /// then
        then(customerRepository).should().save(captor.capture());
        Customer customer1=captor.getValue();
        assertThat(customer1.getRoles()).hasSize(0);



    }
}