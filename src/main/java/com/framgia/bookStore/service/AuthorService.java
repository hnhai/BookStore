package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.AuthorEnity;

import java.util.List;

public interface AuthorService {
    List<AuthorEnity> loadAll();
}
