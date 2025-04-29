package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final AuthRepository authRepository;
    private final EmployeeRepository employeeRepository;

    //get
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public void registerEmployee(EmployeeDTO employeeDTO){
        employeeDTO.setRole("EMPLOYEE");
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        User user = new User(null, employeeDTO.getUsername(), hashPassword, employeeDTO.getName(), employeeDTO.getEmail(), employeeDTO.getRole(), null,null);
        Employee employee = new Employee(null, employeeDTO.getPosition(), employeeDTO.getSalary(), user);
        authRepository.save(user);
        employeeRepository.save(employee);
    }

//    public void reg(EmployeeDTO employeeDTO, User user){
//        user.setRole("EMPLOYEE");
//        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
//        user.setPassword(hashPassword);
//        authRepository.save(user);
//
//        Employee employee = new Employee(null, employeeDTO.getPosition(), employeeDTO.getSalary(), user);
//        employeeRepository.save(employee);
//    }

    public void updateEmployee(Integer emp_id,EmployeeDTO employeeDTO){
        Employee oldEmp = employeeRepository.findEmployeeById(emp_id);
        if(oldEmp == null){
            throw new ApiException("Employee not found");
        }
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        oldEmp.setPosition(employeeDTO.getPosition());
        oldEmp.setSalary(employeeDTO.getSalary());
        oldEmp.getUser().setPassword(hashPassword);
        employeeRepository.save(oldEmp);
    }

    public void deleteEmployee(Integer emp_id){
        Employee employee = employeeRepository.findEmployeeById(emp_id);
        if(employee == null){
            throw new ApiException("Employee not found");
        }
        employeeRepository.delete(employee);
    }


}
