package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.CategoryEntity;
import com.framgia.bookStore.form.CategoryChart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> loadAll();
    List<CategoryChart> loadCategoryChart(Integer month, Integer year);
    Page<CategoryEntity> loadAll(Pageable pageable);
}
