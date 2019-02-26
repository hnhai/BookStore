package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookReponsitory extends CustomJpaRepository<BookEntity, Long>{

    BookEntity getByDeletedAndId(Boolean deleted, Long id);

    BookEntity getByAliasNameAndDeleted(String aliasName, Boolean deleted);

    BookEntity getByAliasName(String aliasName);

    // Query get TOP 5 books most sell
    @Query(value = "SELECT b FROM BookEntity b JOIN b.orderDetails d WHERE b.deleted = FALSE GROUP BY b.id ORDER BY SUM(d.quantity) DESC")
    List<BookEntity> getTop5Book(Pageable pageable);

    @Query(value = "FROM BookEntity b WHERE b.deleted = FALSE ORDER BY b.id DESC")
    List<BookEntity> get10Books(Pageable pageable);

    @Query("SELECT b FROM BookEntity b JOIN b.authors a WHERE b.deleted = FALSE AND b.name LIKE %:name% AND a.name LIKE %:author% AND (b.price BETWEEN :num1 AND :num2)")
    Page<BookEntity> getByNameAuthorAndPrice(Pageable pageable, @Param("name") String name, @Param("author") String author, long num1, long num2);

    @Query("SELECT b FROM BookEntity b WHERE b.deleted = FALSE AND b.name LIKE %:name%")
    Page<BookEntity> getByName(Pageable pageable, @Param("name") String name);

    @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authors a WHERE b.deleted = FALSE AND b.name LIKE %:name% AND a.name LIKE %:author%")
    Page<BookEntity> getByNameAuthor(Pageable pageable, @Param("name") String name, @Param("author") String author);

    @Query("SELECT DISTINCT b FROM BookEntity b WHERE b.deleted = FALSE AND b.name LIKE %:name% AND (b.price BETWEEN :num1 AND :num2) ")
    Page<BookEntity> getByNamePrice(Pageable pageable, @Param("name") String name, long num1, long num2);

    @Query("SELECT b FROM BookEntity b WHERE b.deleted = FALSE AND b.price BETWEEN :num1 AND :num2")
    Page<BookEntity> getAllByPriceBetween(Pageable pageable, long num1, long num2);

    @Query("SELECT b FROM BookEntity b JOIN b.authors a WHERE b.deleted = FALSE AND a.name LIKE %:author%")
    Page<BookEntity> getByAuthor(Pageable pageable, @Param("author") String author);

    @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authors a WHERE b.deleted = FALSE AND a.name LIKE %:author% AND (b.price BETWEEN :num1 AND :num2)")
    Page<BookEntity> getByAuthorPirce(Pageable pageable, @Param("author") String author, long num1, long num2);

    Page<BookEntity> getAllByDeleted(Pageable pageable, Boolean deleted);

    @Transactional
    @Modifying
    @Query("UPDATE BookEntity b SET b.deleted = TRUE WHERE b.id = ?1")
    void updateDeletedById(Long id);
}
