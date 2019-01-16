package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.constants.RoleType;
import com.framgia.bookStore.dto.user.RegisterForm;
import com.framgia.bookStore.entity.RoleEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.entity.UserRoleEntity;
import com.framgia.bookStore.entity.UserRoleId;
import com.framgia.bookStore.repository.RoleRepository;
import com.framgia.bookStore.repository.UserRepository;
import com.framgia.bookStore.repository.UserRoleRopository;
import com.framgia.bookStore.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRopository userRoleRopository;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsernameAndDeleted(username, false);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmailAndDeleted(email, false);
    }

    @Override
    @Transactional
    public UserEntity saveUser(RegisterForm form) {
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail().trim());
        user.setUsername(form.getUsername().trim());
        user.setPassword(passwordEncoder.encode(form.getPassword().trim()));
        user.setGender(form.getGender());
        user.setEnabled(true);
        user.setDeleted(false);
        user.setStatus(true);
        user = userRepository.save(user);
        RoleEntity role = roleRepository.findByRoleType(RoleType.ROLE_USER);
        UserRoleEntity userRole = new UserRoleEntity(user, role);
        userRole.setId(new UserRoleId(user.getId(), role.getId()));
        userRole.setDeleted(false);
        userRoleRopository.save(userRole);
        return user;
    }

    @Override
    public Boolean duplicateEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean duplicateUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean resetPassword(String usernameOrEmail) {
        UserEntity user = userRepository.findByUsernameAndDeleted(usernameOrEmail, false);
        if(user == null){
            user = userRepository.findByEmailAndDeleted(usernameOrEmail, false);
        }
        if (user != null){
            String token = RandomStringUtils.random(45, true, false);
            user.setToken(token);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
