package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReponsitory extends CustomJpaRepository<BookEntity, Long>{
    BookEntity getByAliasName(String aliasName);

    // Query get TOP 5 books most sell
    @Query(value = "SELECT b FROM BookEntity b JOIN b.orderDetails d WHERE b.deleted = FALSE GROUP BY b.id ORDER BY SUM(d.quantity) DESC")
    Page<BookEntity> getTop5Book(Pageable pageable);

    @Query(value = "FROM BookEntity b ORDER BY b.id DESC")
    List<BookEntity> get10Books(Pageable pageable);
}
