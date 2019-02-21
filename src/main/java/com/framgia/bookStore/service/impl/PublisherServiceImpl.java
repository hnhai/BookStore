package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.PublisherEntity;
import com.framgia.bookStore.repository.PublisherRepository;
import com.framgia.bookStore.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<PublisherEntity> loadAll() {
        return publisherRepository.findAll();
    }
}
