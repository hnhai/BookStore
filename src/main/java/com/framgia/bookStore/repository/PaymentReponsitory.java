package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentReponsitory extends CustomJpaRepository<PaymentEntity, Long>{
}
