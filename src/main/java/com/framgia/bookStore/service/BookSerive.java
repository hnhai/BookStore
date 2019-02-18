package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.BookEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookSerive {
    List<BookEntity> loadAll();
    BookEntity getByAliasName(String aliasName);
    Page<BookEntity> getTop5Book();
    List<BookEntity> get10Books();
}
