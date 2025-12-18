package com.suivi_academique.security;

import com.suivi_academique.entities.Personnel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final String codePersonnel;  // ← ID utilisateur
    private final String loginPersonnel;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return loginPersonnel;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}