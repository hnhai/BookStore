package com.framgia.bookStore.service;

import com.framgia.bookStore.dto.user.RegisterForm;
import com.framgia.bookStore.entity.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    UserEntity saveUser(RegisterForm form);

    Boolean duplicateEmail(String email);

    Boolean duplicateUsername(String username);

    Boolean resetPassword(String usernameOrEmail);
}
