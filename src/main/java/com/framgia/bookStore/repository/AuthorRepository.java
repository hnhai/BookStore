package com.framgia.bookStore.repository;

import com.framgia.bookStore.entity.AuthorEnity;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CustomJpaRepository<AuthorEnity, Long>{
}
