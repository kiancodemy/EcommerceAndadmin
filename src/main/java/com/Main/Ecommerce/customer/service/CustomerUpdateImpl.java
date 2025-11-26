package com.Main.Ecommerce.customer.service;

import com.Main.Ecommerce.auth.enums.EnumRole;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.model.Role;
import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.repository.RoleRepository;
import com.Main.Ecommerce.customer.dto.CustomerUpdateRequest;
import com.Main.Ecommerce.customer.filter.CustomerSearchFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerUpdateImpl implements CustomerUpdateService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final CustomerSearchFilter customerSearchFilter;

    /// tested ///user
    @Override
    public Customer updateCustomer(String email, CustomerUpdateRequest request) {

        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("ایمیمل معتبر نیست"));
        customer.setEmail(request.email());
        customer.setName(request.name());
        customer.setGender(request.gender());
        return customerRepository.save(customer);
    }

    //// tested //// admin
    @Override
    public Role createRole(EnumRole enumRole){
        roleRepository.findByRole(enumRole)
                .ifPresent(r -> {
                    throw new RuntimeException("از قبل موجود است");
                });

        Role createRole=Role.builder().role(enumRole).build();
        return roleRepository.save(createRole);
    }

    /// tested ///admin
    @Override
    public Customer addRoleToCustomer(Long customerId, Long roleId) {

        Customer findCustomer=customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("ایمیمل موجود نبود"));
        Role findRole=roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("نقش موجود نبود"));
        findCustomer.getRoles().add(findRole);
        return customerRepository.save(findCustomer);

     }

     /// tested /// admin
    @Override
    public Customer removeRoleFromCustomer(Long customerId, Long roleId) {

        Customer findCustomer=customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("ایمیمل موجود نبود"));
        Role findRole=roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("نقش موجود نبود"));

        findCustomer.getRoles().remove(findRole);
        return customerRepository.save(findCustomer);
    }


    @Override
    public Page<Customer> allCustomers(int page, int size, String name) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        Pageable pageable= PageRequest.of(page, size, sort);
        return customerRepository.findAll(customerSearchFilter.searchCustomerByName(name),pageable);
    }
}
