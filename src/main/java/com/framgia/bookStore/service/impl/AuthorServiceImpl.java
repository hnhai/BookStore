package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.repository.AuthorRepository;
import com.framgia.bookStore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<AuthorEnity> loadAll() {
        return authorRepository.findAll();
    }
}
