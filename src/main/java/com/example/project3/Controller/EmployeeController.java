package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/emp")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    //get
    @GetMapping("/get")
    public ResponseEntity getAllEmployee(){
        return ResponseEntity.status(200).body(employeeService.getAllEmployee());
    }

    //Register
    @PostMapping("/register")
    public ResponseEntity registerEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Employee registered"));
    }

    //update
    @PutMapping("/update")
    public ResponseEntity updateEmp(@AuthenticationPrincipal User user, @RequestBody @Valid EmployeeDTO employeeDTO){
        employeeService.updateEmployee(user.getId(), employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Updated emp"));
    }
    @DeleteMapping("/del/{emp_id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer emp_id){
        employeeService.deleteEmployee(emp_id);
        return ResponseEntity.status(200).body(new ApiResponse("Deleted employee"));
    }
}
