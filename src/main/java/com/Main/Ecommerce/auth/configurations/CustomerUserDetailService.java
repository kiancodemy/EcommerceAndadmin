package com.Main.Ecommerce.auth.configurations;

import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  customerRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("ایمیل معتبر نیست"));
    }
}
