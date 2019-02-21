package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.PublisherEntity;

import java.util.List;

public interface PublisherService {
    List<PublisherEntity> loadAll();
}
