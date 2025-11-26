package com.Main.Ecommerce.customer;

import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.customer.dto.CustomerDto;
import com.Main.Ecommerce.customer.dto.CustomerUpdateRequest;
import com.Main.Ecommerce.customer.service.CustomerUpdateImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerUpdateController {
    private final CustomerUpdateImpl customerUpdate;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<Response> update(Authentication authentication, @Valid @RequestBody CustomerUpdateRequest request) {
        Customer customer =(Customer) authentication.getPrincipal();
        String email = customer.getEmail();
        Customer updateCustomer = customerUpdate.updateCustomer(email, request);
        CustomerDto updatedCustomerDto = modelMapper.map(updateCustomer, CustomerDto.class);
        return ResponseEntity.ok().body(new Response("با موفقیت به روز رسانی شد",updatedCustomerDto));
    }
}
