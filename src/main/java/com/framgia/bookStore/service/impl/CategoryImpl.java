package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.entity.CategoryEntity;
import com.framgia.bookStore.form.CategoryChart;
import com.framgia.bookStore.repository.CategoryReponsitory;
import com.framgia.bookStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryReponsitory categoryReponsitory;

    @Override
    public List<CategoryEntity> loadAll() {
        return categoryReponsitory.findAll();
    }

    @Override
    public List<CategoryChart> loadCategoryChart(Integer month, Integer year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<CategoryChart> charts = new ArrayList<>();
        List<CategoryEntity> categories = categoryReponsitory.getCategoryOfMonth(convertToDateViaSqlDate(startDate), convertToDateViaSqlDate(endDate));
        for (CategoryEntity c : categories) {
            CategoryChart cate = new CategoryChart();
            cate.setName(c.getName());
            int total = 0;
            for (BookEntity b: c.getBooks()) {
                total += b.getOrderDetails().size();
            }
            List<Integer> integers = new ArrayList<>();
            integers.add(total);
            cate.setData(integers);
            charts.add(cate);
        }
        return charts;
    }

    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
