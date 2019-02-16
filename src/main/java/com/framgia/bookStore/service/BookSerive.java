package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.BookEntity;

import java.util.List;

public interface BookSerive {
    List<BookEntity> loadAll();
    BookEntity getByAliasName(String aliasName);
}
