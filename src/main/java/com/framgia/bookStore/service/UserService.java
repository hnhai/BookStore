package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}
