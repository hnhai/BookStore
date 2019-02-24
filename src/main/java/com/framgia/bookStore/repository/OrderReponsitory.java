package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.OrderEntity;
import com.framgia.bookStore.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderReponsitory extends CustomJpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByUserAndDeleted(Pageable page, UserEntity user, Boolean deleted);
    Page<OrderEntity> findAllByDeleted(Pageable pageable, Boolean deleted);
}
