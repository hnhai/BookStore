package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.BookEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReponsitory extends CustomJpaRepository<BookEntity, Long>{
    BookEntity getByAliasName(String aliasName);
}
