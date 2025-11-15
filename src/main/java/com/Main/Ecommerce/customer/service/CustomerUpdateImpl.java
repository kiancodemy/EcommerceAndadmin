package com.Main.Ecommerce.customer.service;

import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.model.Role;
import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.repository.RoleRepository;
import com.Main.Ecommerce.customer.dto.CustomerUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerUpdateImpl implements CustomerUpdateService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    /// tested
    @Override
    public Customer updateCustomer(String email, CustomerUpdateRequest request) {

        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("ایمیمل معتبر نیست"));
        customer.setEmail(request.email());
        customer.setName(request.name());
        customer.setGender(request.gender());
        return customerRepository.save(customer);
    }

    /// tested
    @Override
    public Customer addRoleToCustomer(String email, Long roleId) {

        Customer findCustomer=customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("ایمیمل موجود نبود"));
        Role findRole=roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("نقش موجود نبود"));
        findCustomer.getRoles().add(findRole);
        return customerRepository.save(findCustomer);

     }

    /// tested
    @Override
    public Customer removeRoleFromCustomer(String email, Long roleId) {

        Customer findCustomer=customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("ایمیمل موجود نبود"));
        Role findRole=roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("نقش موجود نبود"));

        findCustomer.getRoles().remove(findRole);
        return customerRepository.save(findCustomer);
    }
}
