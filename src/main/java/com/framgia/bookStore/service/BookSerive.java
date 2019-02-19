package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookSerive {
    List<BookEntity> loadAll();
    BookEntity getByAliasName(String aliasName);
    List<BookEntity> getTop5Book();
    List<BookEntity> get10Books();
    Page<BookEntity> findAll(Pageable pageable);
    Page<BookEntity> findBook(Pageable pageable, String name, String author, String price1, String price2);
}
