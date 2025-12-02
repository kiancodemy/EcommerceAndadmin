package com.Main.Ecommerce.admin.controller;

import com.Main.Ecommerce.auth.dto.response.Response;
import com.Main.Ecommerce.auth.dto.response.RoleDto;
import com.Main.Ecommerce.auth.enums.EnumRole;
import com.Main.Ecommerce.auth.model.Customer;
import com.Main.Ecommerce.auth.model.Role;
import com.Main.Ecommerce.customer.dto.CustomerDto;
import com.Main.Ecommerce.customer.dto.CustomerPageDto;
import com.Main.Ecommerce.customer.service.CustomerUpdateImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/customer")
@Tag(name = "admin-customer-handler", description = "APIs for customer by admin")
////@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final CustomerUpdateImpl customerUpdate;
    private final ModelMapper modelMapper;

    @PutMapping("/addRole/{customerId}/{roleId}")
    public ResponseEntity<Response> addRoleToCustomer(@PathVariable("customerId") Long customerId,@PathVariable("roleId") Long roleId){

        Customer updatedCustomer=customerUpdate.addRoleToCustomer(customerId,roleId);
        CustomerDto updatedCustomerDto=modelMapper.map(updatedCustomer, CustomerDto.class);

        return ResponseEntity.ok().body(new Response("با موفقیت اضافه شد",updatedCustomerDto));
    }

    @PostMapping("/createRole/{name}")
    public ResponseEntity<Response> createRoleCustomer(@PathVariable EnumRole name){

        Role createdRole=customerUpdate.createRole(name);
        RoleDto createdRoleDto =modelMapper.map(createdRole, RoleDto.class);
        return ResponseEntity.ok().body(new Response("با موفقیت اضافه شد",createdRoleDto));
    }


    @PutMapping("/removeRole/{customerId}/{id}")
    public ResponseEntity<Response> removeRoleToCustomer(@PathVariable("customerId") Long customerId,@PathVariable("id") Long id){

        Customer updatedCustomer=customerUpdate.removeRoleFromCustomer(customerId,id);
        CustomerDto updatedCustomerDto=modelMapper.map(updatedCustomer, CustomerDto.class);

        return ResponseEntity.ok().body(new Response("با موفقیت حذف شد",updatedCustomerDto));
    }

    @GetMapping("/allCustomers")
    public ResponseEntity<Response> allCustomers(   @RequestParam(defaultValue = "0", required = false) int page,

                                                    @RequestParam(defaultValue = "5", required = false) int size,
                                                    @RequestParam( required = false) String name){

        Page<Customer> allCustomer=customerUpdate.allCustomers(page,size,name);
        CustomerPageDto customerPageDto=modelMapper.map(allCustomer, CustomerPageDto.class);
        return ResponseEntity.ok().body(new Response("با موفقیت انجام شد",customerPageDto));
    }

}
