package com.framgia.bookStore.repository;

import com.framgia.bookStore.constants.RoleType;
import com.framgia.bookStore.entity.RoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CustomJpaRepository<RoleEntity, Long>{
    RoleEntity findByRoleType(RoleType type);
}
