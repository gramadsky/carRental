package com.gramadsky.security;

import com.gramadsky.model.entity.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class LoginDetails implements UserDetails {
    private final Login login;

    public LoginDetails(Login login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority(login.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.login.getPassword();
    }

    @Override
    public String getUsername() {
        return this.login.getUsername();
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

    // Нужно, чтобы получать данные аутентифицированного пользователя
    public Login getLogin() {
        return this.login;
    }

    public Integer getUserId() {
        return this.login.getUser().getId();
    }


}