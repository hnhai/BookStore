package com.framgia.bookStore.auth;

import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("customUserDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            user = userService.findByEmail(username);
        }
        if (user == null || !user.getStatus())
        {
            throw new UsernameNotFoundException("User is not found");
        }
        return createUserDetails(user);
    }

    private CustomUserDetail createUserDetails(UserEntity user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getUserRoleEntities().forEach(e -> authorities.add(new SimpleGrantedAuthority(e.getRoleEntity().getRoleType().toString())));
        return new CustomUserDetail(user, authorities);
    }
}
