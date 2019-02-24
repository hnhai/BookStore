package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.auth.CustomUserDetail;
import com.framgia.bookStore.entity.OrderDetailEntity;
import com.framgia.bookStore.entity.OrderEntity;
import com.framgia.bookStore.entity.UserEntity;
import com.framgia.bookStore.form.OrderDTO;
import com.framgia.bookStore.repository.OrderDetailReponsitory;
import com.framgia.bookStore.repository.OrderReponsitory;
import com.framgia.bookStore.repository.UserRepository;
import com.framgia.bookStore.service.OrderService;
import com.framgia.bookStore.service.userPermissionService;
import com.framgia.bookStore.util.SecurityUtil;
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

    @Autowired
    private OrderDetailReponsitory orderDetailReponsitory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private userPermissionService userPermissionService;

    @Override
    public Page<OrderDTO> loadAllByUser(Pageable pageable, UserEntity user) {
        List<OrderEntity> orders = orderReponsitory.findByUserAndDeleted(pageable, user, false).getContent();
        return convert(orders, pageable);
    }

    @Override
    public Page<OrderDTO> loadAll(Pageable pageable) {
        List<OrderEntity> orders = orderReponsitory.findAllByDeleted(pageable, false).getContent();
        return convert(orders, pageable);
    }

    @Override
    public Boolean updateOrder(Long id, Integer startus) {
        OrderEntity order = orderReponsitory.getOne(id);
        if(order == null){
            return false;
        }
        order.setStatus(startus);
        orderReponsitory.save(order);
        return true;
    }

    @Override
    public List<OrderDetailEntity> loadDetail(Long id) {
        OrderEntity order = orderReponsitory.getOne(id);
        if(order == null){
            return null;
        }
        return orderDetailReponsitory.findAllByOrderAndDeleted(order, false);
    }

    @Override
    public Boolean canViewOrder(Long orderId) {
        CustomUserDetail userDetail = SecurityUtil.getCurrentUser();
        UserEntity userEntity = userRepository.findByUsername(userDetail.getUsername());
        OrderEntity orderEntity = orderReponsitory.getOne(orderId);
        if(!orderEntity.getUser().getUsername().equalsIgnoreCase(userEntity.getUsername())
        && !userPermissionService.isEmployee(userEntity)){
            return false;
        }
        return true;
    }

    private Page<OrderDTO> convert(List<OrderEntity> orders, Pageable pageable){
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (OrderEntity o: orders) {
            orderDTOs.add(TranferUtil.orderTranfer(o));
        }
        Long start = pageable.getOffset();
        Long end = (start + pageable.getPageSize()) > orderDTOs.size() ? orderDTOs.size() : (start + pageable.getPageSize());
        return new PageImpl<OrderDTO>(orderDTOs.subList(start.intValue(), end.intValue()), pageable, orderDTOs.size());
    }
}
