package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.PublisherEntity;
import com.framgia.bookStore.form.PublisherDTO;
import com.framgia.bookStore.repository.PublisherRepository;
import com.framgia.bookStore.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<PublisherEntity> loadAllPublisher(Pageable pageable) {
        return publisherRepository.getByDeleted(Boolean.FALSE, pageable);
    }

    @Override
    public Boolean addPublisher(PublisherDTO publisherDTO) {
        try {
            PublisherEntity publisher = new PublisherEntity();
            publisher.setName(publisherDTO.getName());
            publisher.setAddress(publisherDTO.getAddress());
            publisher.setPhoneNumber(publisherDTO.getPhoneNumber());
            publisher.setDescription(publisherDTO.getDescription());
            publisherRepository.save(publisher);
            return Boolean.TRUE;
        }catch (Exception e){
            //Todo Refactor
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deletePublishers(List<Long> ids) {
        ids.forEach(id->publisherRepository.deleteById(id));
        return true;
    }
}
