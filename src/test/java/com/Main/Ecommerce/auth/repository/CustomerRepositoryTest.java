package com.Main.Ecommerce.auth.repository;
import com.Main.Ecommerce.auth.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder().password("123").email("a@gmail.com").build();
    }


    @Test
    void it_Should_FindByEmail() {
        ///given
        customerRepository.save(customer);

        /// when
        Optional<Customer> findCustomer = customerRepository.findByEmail(customer.getEmail());

        /// then
       assertThat(findCustomer).isPresent();
        assertThat(findCustomer.get().getEmail()).isEqualTo("a@gmail.com");

    }

}