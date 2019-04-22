package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.PublisherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PublisherRepository extends CustomJpaRepository<PublisherEntity, Long>{
    PublisherEntity getByNameAndDeleted(String name, Boolean deleted);
    Page<PublisherEntity> getByDeleted(Boolean deleted, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE PublisherEntity p SET p.deleted = TRUE WHERE p.id = ?1")
    void deleteById(Long id);
}
