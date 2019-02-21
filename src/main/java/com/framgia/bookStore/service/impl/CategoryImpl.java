package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.CategoryEntity;
import com.framgia.bookStore.repository.CategoryReponsitory;
import com.framgia.bookStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryReponsitory categoryReponsitory;

    @Override
    public List<CategoryEntity> loadAll() {
        return categoryReponsitory.findAll();
    }
}
