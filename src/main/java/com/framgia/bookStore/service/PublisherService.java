package com.framgia.bookStore.service;

import com.framgia.bookStore.entity.PublisherEntity;
import com.framgia.bookStore.form.PublisherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublisherService {
    List<PublisherEntity> loadAll();
    Page<PublisherEntity> loadAllPublisher(Pageable pageable);
    Boolean addPublisher(PublisherDTO publisherDTO);
    Boolean deletePublishers(List<Long> ids);
}
