package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AuthorRepository extends CustomJpaRepository<AuthorEnity, Long>{
    List<AuthorEnity> getAllByBooks(BookEntity bookEntity);

    AuthorEnity getByNameAndDeleted(String name, Boolean deleted);

    Page<AuthorEnity> getByDeleted(Boolean deleted, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE AuthorEnity a SET a.deleted = TRUE WHERE a.id = ?1")
    void deleteById(Long id);
}
