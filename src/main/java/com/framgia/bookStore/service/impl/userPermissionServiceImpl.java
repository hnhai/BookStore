package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.auth.CustomUserDetail;
import com.framgia.bookStore.constants.RoleType;
import com.framgia.bookStore.entity.OrderEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.entity.UserRoleEntity;
import com.framgia.bookStore.repository.OrderReponsitory;
import com.framgia.bookStore.repository.UserRepository;
import com.framgia.bookStore.service.UserService;
import com.framgia.bookStore.service.userPermissionService;
import com.framgia.bookStore.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userPermissionServiceImpl implements userPermissionService {

    @Autowired
    private OrderReponsitory orderReponsitory;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity getCurrentUser() {
        CustomUserDetail customUserDetail = SecurityUtil.getCurrentUser();
        return customUserDetail != null ? userService.findById(customUserDetail.getUserEntity().getId()) : null;
    }

    @Override
    public Boolean isAdmin(UserEntity user) {
        return user.getUserRoleEntities().stream().map(UserRoleEntity::getRoleEntity)
                .anyMatch(e -> e.getRoleType().equals(RoleType.ROLE_ADMIN));
    }

    @Override
    public Boolean isEmployee(UserEntity user) {
        return user.getUserRoleEntities().stream().map(UserRoleEntity::getRoleEntity)
                .anyMatch(e -> e.getRoleType().equals(RoleType.ROLE_EMPLOYEE));
    }

    @Override
    public Boolean canViewOrder(Long orderId) {
        CustomUserDetail userDetail = SecurityUtil.getCurrentUser();
        UserEntity userEntity = userRepository.findByUsername(userDetail.getUsername());
        OrderEntity orderEntity = orderReponsitory.getOne(orderId);
        if(!orderEntity.getUser().getUsername().equalsIgnoreCase(userEntity.getUsername())
                && !isEmployee(userEntity)){
            return false;
        }
        return true;
    }
}
