package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "role = 'CUSTOMER' OR role = 'EMPLOYEE' OR role = 'ADMIN'")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Can not be empty")
    @Size(min = 3,max = 10,message = "Length must be between 4 and 10 characters")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;
    @NotEmpty(message = "Can not be empty")
    @Size(min = 6,max = 100, message = "Length must be at least 6 characters")
    @Column(columnDefinition = "varchar(200) not null")
    private String password;
    @NotEmpty(message = "Can not be empty")
    @Size(min = 2,max = 20,message = "Length must be between 2 and 20 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @Email
    @Column(columnDefinition = "varchar(60) not null unique")
    private String email;
    @Column(columnDefinition = "varchar(20) not null")
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "Role must be CUSTOMER, EMPLOYEE, or ADMIN only")
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
