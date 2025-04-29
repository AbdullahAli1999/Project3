package com.example.project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    @NotNull(message = "Username must not be null")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    private String username;

    @NotNull(message = "Password must not be null")
    @Size(min = 6,max = 100, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Name must not be null")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")
    private String email;
    @Column(columnDefinition = "varchar(20) not null")
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "Role must be CUSTOMER, EMPLOYEE, or ADMIN only")
    private String role;

    @NotEmpty(message = "Can not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String position;

    @NotNull(message = "Salary must not be null")
    @PositiveOrZero(message = "Salary must be non-negative")
    private Double salary;

}
