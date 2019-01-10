package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CustomJpaRepository<UserEntity, Long> {
    UserEntity findByUsernameAndDeleted(String userName, Boolean deleted);

    UserEntity findByEmailAndDeleted(String email, Boolean deleted);
}
