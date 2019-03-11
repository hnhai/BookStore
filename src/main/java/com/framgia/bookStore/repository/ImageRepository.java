package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.entity.ImageEntity;

public interface ImageRepository extends CustomJpaRepository<ImageEntity, Long>{
    Integer deleteByNameAndAndBook(String name, BookEntity book);
}
