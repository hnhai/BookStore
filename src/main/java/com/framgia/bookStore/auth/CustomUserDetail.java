package com.framgia.bookStore.auth;

import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.service.UserService;
import com.framgia.bookStore.util.ApplicationContextProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetail implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = 1L;

    private UserEntity user;

    private final Set<GrantedAuthority> authorities;

    private List<String> roleIds = new ArrayList<>();

    public UserEntity getUser() {
        return getUser(false);
    }

    public UserEntity getUser(boolean reload) {
        if (reload && user != null) {
            UserService userService = ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
            this.user = userService.findByUsername(user.getUsername());
            this.eraseCredentials();
        }
        return user;
    }

    CustomUserDetail(UserEntity user, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<String> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getAvatar() {
        if (StringUtils.isBlank(user.getAvatar())) {
            user.getAvatar();
        }
        return user.getAvatar();
    }

    public String getFullName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public void eraseCredentials() {
        if (user != null) {
            user.setPassword(null);
            user.setVersion(null);
            user.setCreatedBy(null);
            user.setCreatedDate(null);
            user.setLastModifiedBy(null);
            user.setLastModifiedDate(null);
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return this.user.getEnabled();
    }

    public void addAuthority(GrantedAuthority authority) {
        this.authorities.add(authority);
    }

    public void addRoleId(String roleId) {
        this.roleIds.add(roleId);
    }

    public boolean hasAuthority(String authId) {
        for (GrantedAuthority authority : authorities) {
            if (authId.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRole(String roleId) {
        return roleIds.contains(roleId);
    }
}
