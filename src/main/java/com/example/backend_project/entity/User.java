package com.example.backend_project.entity;

import com.example.backend_project.enums.UserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotBlank
    private String username;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "password")
    @NotBlank
    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name="last_modified_date")
    private LocalDateTime lastModifiedDate;
    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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