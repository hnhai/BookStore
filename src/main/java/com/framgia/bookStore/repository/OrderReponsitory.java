package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReponsitory extends CustomJpaRepository<OrderEntity, Long> {
}
