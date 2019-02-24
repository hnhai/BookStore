package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.OrderDetailEntity;
import com.framgia.bookStore.entity.OrderEntity;

import java.util.List;

public interface OrderDetailReponsitory extends CustomJpaRepository<OrderDetailEntity, Long>{
    List<OrderDetailEntity> findAllByOrderAndDeleted(OrderEntity orderEntity, Boolean deleted);
}
