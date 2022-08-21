package com.spreadsearch.models;

import com.spreadsearch.models.enums.Role;
import com.spreadsearch.models.enums.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @Column(name = "username", unique = true, updatable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription")
    private Subscription subscription;

    @Column(name = "password", length = 1000)
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "activate_code")
    private String activateCode;

    @Column(name = "reset_password_code")
    private String resetPasswordCode;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Column(name = "date_of_created", updatable = false)
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void onCreate() {
        dateOfCreated = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
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
        return active;
    }


}
