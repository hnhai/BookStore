package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CategoryReponsitory extends CustomJpaRepository<CategoryEntity, Long> {
    @Query("SELECT distinct c FROM CategoryEntity c JOIN c.books b JOIN b.orderDetails od JOIN od.order o WHERE o.buyDay BETWEEN :startDate AND :endDate")
    List<CategoryEntity> getCategoryOfMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
