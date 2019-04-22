package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.form.AuthorDTO;
import com.framgia.bookStore.repository.AuthorRepository;
import com.framgia.bookStore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<AuthorEnity> loadAllAuthors(Pageable pageable) {
        return authorRepository.getByDeleted(false, pageable);
    }

    @Override
    public Boolean addAuthor(AuthorDTO authorDTO) {
        try {
            AuthorEnity author = new AuthorEnity();
            author.setName(authorDTO.getName());
            author.setAddress(authorDTO.getAddress());
            author.setPhoneNumber(authorDTO.getPhoneNumber());
            author.setDescription(authorDTO.getDescription());
            authorRepository.save(author);
            return true;
        }catch (Exception e){
            // Todo Refacter
        }
        return false;
    }

    @Override
    public Boolean deleteAuthors(List<Long> ids) {
        ids.forEach(id->authorRepository.deleteById(id));
        return true;
    }
}
