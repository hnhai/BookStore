package com.framgia.bookStore.service;

import com.framgia.bookStore.dto.user.RegisterForm;
import com.framgia.bookStore.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity saveUser(RegisterForm form);

    UserEntity findByUsernameAndToken(String username, String token);

    Boolean duplicateEmail(String email);

    Boolean duplicateUsername(String username);

    Boolean resetPassword(String usernameOrEmail, HttpServletRequest request);

    UserEntity updatePassword(Long userId, String password);

    UserEntity findById(Long id);

    Page<UserEntity> findAll(Pageable pageable);
}
