package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.PublisherEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CustomJpaRepository<PublisherEntity, Long>{
    PublisherEntity getByNameAndDeleted(String name, Boolean deleted);
}
