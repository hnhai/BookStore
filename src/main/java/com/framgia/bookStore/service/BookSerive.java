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
    Page<BookEntity> findAllByName(Pageable pageable, String name);
    Page<BookEntity> findAllByPrice(Pageable pageable, Long num1, Long num2);
    Page<BookEntity> findAllByAuthor(Pageable pageable, String author);
}
