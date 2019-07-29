package com.framgia.bookStore.auth;

import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.service.UserService;
import com.framgia.bookStore.util.ApplicationContextProvider;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails, CredentialsContainer {

    public CustomUserDetail(UserEntity userEntity, List<GrantedAuthority> authorities) {
        this.userEntity = userEntity;
        this.authorities = authorities;
    }

    private UserEntity userEntity;

    private List<GrantedAuthority> authorities;

    public UserEntity getUser() {
        return userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public void eraseCredentials() {
        if (userEntity != null) {
            userEntity.setPassword(null);
            userEntity.setVersion(null);
            userEntity.setCreatedBy(null);
            userEntity.setCreatedDate(null);
            userEntity.setLastModifiedBy(null);
            userEntity.setLastModifiedDate(null);
        }
    }
}
