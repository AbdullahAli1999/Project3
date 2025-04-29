package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public void registerCustomer(CustomerDTO customerDTO){
        customerDTO.setRole("CUSTOMER");
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        User user = new User(null, customerDTO.getUsername(), hashPassword, customerDTO.getName(), customerDTO.getEmail(), customerDTO.getRole(), null,null);
        Customer customer = new Customer(null, customerDTO.getPhoneNumber(), user,null);
        authRepository.save(user);
        customerRepository.save(customer);
    }

    public void updateCustomer(Integer customer_id, CustomerDTO customerDTO){
        Customer oldCustomer = customerRepository.findCustomerByUserId(customer_id);
        if(oldCustomer == null){
            throw new ApiException("Customer not found");
        }
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        oldCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        oldCustomer.getUser().setPassword(hashPassword);
        customerRepository.save(oldCustomer);
    }

    public void deleteCustomer(Integer customer_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw new ApiException("Customer not found");
        }
        customerRepository.delete(customer);
    }

}
