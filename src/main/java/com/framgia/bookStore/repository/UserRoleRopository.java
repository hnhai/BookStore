package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRopository extends CustomJpaRepository<UserRoleEntity, Long>{
}
