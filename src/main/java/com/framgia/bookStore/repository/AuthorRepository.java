package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.entity.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CustomJpaRepository<AuthorEnity, Long>{
    List<AuthorEnity> getAllByBooks(BookEntity bookEntity);

    AuthorEnity getByNameAndDeleted(String name, Boolean deleted);
}
