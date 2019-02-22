package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.form.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDTO> loadAllByUser(Pageable pageable, UserEntity user);
}
