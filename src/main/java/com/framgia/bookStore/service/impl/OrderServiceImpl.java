package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.OrderEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.form.OrderDTO;
import com.framgia.bookStore.repository.OrderReponsitory;
import com.framgia.bookStore.service.OrderService;
import com.framgia.bookStore.util.TranferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderReponsitory orderReponsitory;

    @Override
    public Page<OrderDTO> loadAllByUser(Pageable pageable, UserEntity user) {
        List<OrderEntity> orders = orderReponsitory.findByUserAndDeleted(pageable, user, false).getContent();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (OrderEntity o: orders) {
            orderDTOs.add(TranferUtil.orderTranfer(o));
        }
        Long start = pageable.getOffset();
        Long end = (start + pageable.getPageSize()) > orderDTOs.size() ? orderDTOs.size() : (start + pageable.getPageSize());
        return new PageImpl<OrderDTO>(orderDTOs.subList(start.intValue(), end.intValue()), pageable, orderDTOs.size());
    }
}
