package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

@Repository
public interface UserRepository extends CustomJpaRepository<UserEntity, Long> {
    UserEntity findByUsernameAndDeleted(String userName, Boolean deleted);

    UserEntity findByUsername(String userName);

    UserEntity findByEmailAndDeleted(String email, Boolean deleted);

    UserEntity findByEmail(String email);

    UserEntity findByUsernameAndTokenAndDeleted(String user, String token, Boolean deleted);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.deleted = TRUE WHERE u.id = ?1")
    void updateDeletedById(Long id);
}
