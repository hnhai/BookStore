package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.dto.user.RegisterForm;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.repository.UserRepository;
import com.framgia.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Boolean saveUser(RegisterForm form) {
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail().trim());
        user.setUsername(form.getUsername().trim());
        user.setPassword(passwordEncoder.encode(form.getPassword().trim()));
        user.setGender(form.getGender());
        user.setSystemUser(false);
        user.setEnabled(true);
        user.setStatus(true);
        user.setDeleted(false);
        user.setCreatedBy(Long.valueOf(-1));
        user.setVersion(Long.valueOf(-1));
        user.setLastModifiedBy(Long.valueOf(-1));
        user.setLastModifiedDate(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        userRepository.save(user);
        return true;
    }
}
