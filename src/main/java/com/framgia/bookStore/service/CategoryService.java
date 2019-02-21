package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> loadAll();
}
