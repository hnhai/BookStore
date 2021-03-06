package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.UserEntity;

public interface userPermissionService {
    UserEntity getCurrentUser();

    Boolean isAdmin(UserEntity user);

    Boolean isEmployee(UserEntity user);

    Boolean canViewOrder(Long orderId);
}
