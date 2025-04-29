package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    //GET
    @GetMapping("/get")
    public ResponseEntity getAllCustomer(){
        return ResponseEntity.status(200).body(customerService.getAllCustomer());
    }

    //Register
    @PostMapping("/register")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Customer registered"));
    }

    //update
    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTO customerDTO){
        customerService.updateCustomer(user.getId(), customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Updated customer"));
    }

    //delete
    @DeleteMapping("/del/{customer_id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer customer_id){
        customerService.deleteCustomer(customer_id);
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Customer"));
    }


}
