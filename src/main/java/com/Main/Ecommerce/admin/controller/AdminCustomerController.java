package com.Main.Ecommerce.admin.controller;

import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.customer.dto.CustomerResponseDto;
import com.Main.Ecommerce.customer.dto.CustomerUpdateRequest;
import com.Main.Ecommerce.customer.service.CustomerUpdateImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final CustomerUpdateImpl customerUpdate;
    private final ModelMapper modelMapper;


    @PutMapping("/addRole/{id}")
    public ResponseEntity<Response> addRoleToCustomer(Authentication authentication,@PathVariable Long id){
        Customer customer=(Customer) authentication.getPrincipal();
        String email=customer.getEmail();
        Customer updatedCustomer=customerUpdate.addRoleToCustomer(email,id);
        CustomerResponseDto updatedCustomerDto=modelMapper.map(updatedCustomer,CustomerResponseDto.class);

        return ResponseEntity.ok().body(new Response("با موفقیت اضافه شد",updatedCustomerDto));
    }

    @PutMapping("/removeRole/{id}")
    public ResponseEntity<Response> removeRoleToCustomer(Authentication authentication,@PathVariable Long id){
        Customer customer=(Customer) authentication.getPrincipal();
        String email=customer.getEmail();
        Customer updatedCustomer=customerUpdate.removeRoleFromCustomer(email,id);
        CustomerResponseDto updatedCustomerDto=modelMapper.map(updatedCustomer,CustomerResponseDto.class);

        return ResponseEntity.ok().body(new Response("با موفقیت حذف شد",updatedCustomerDto));
    }
}
