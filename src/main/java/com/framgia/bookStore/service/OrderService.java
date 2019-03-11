package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.OrderDetailEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.form.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderDTO> loadAllByUser(Pageable pageable, UserEntity user);
    Page<OrderDTO> loadAll(Pageable pageable);
    Boolean updateOrder(Long id, Integer startus);
    List<OrderDetailEntity> loadDetail(Long id);
}
