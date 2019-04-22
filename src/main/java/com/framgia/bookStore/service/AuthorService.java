package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.form.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    List<AuthorEnity> loadAll();
    Page<AuthorEnity> loadAllAuthors(Pageable pageable);
    Boolean addAuthor(AuthorDTO authorDTO);
    Boolean deleteAuthors(List<Long> ids);
}
