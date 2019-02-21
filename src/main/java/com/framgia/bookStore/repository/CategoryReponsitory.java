package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReponsitory extends CustomJpaRepository<CategoryEntity, Long> {
}
