package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.configuration.ResourceConfig;
import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.entity.CategoryEntity;
import com.framgia.bookStore.entity.ImageEntity;
import com.framgia.bookStore.entity.PublisherEntity;
import com.framgia.bookStore.form.AddBook;
import com.framgia.bookStore.form.BookCart;
import com.framgia.bookStore.repository.BookReponsitory;
import com.framgia.bookStore.repository.CategoryReponsitory;
import com.framgia.bookStore.repository.ImageRepository;
import com.framgia.bookStore.repository.PublisherRepository;
import com.framgia.bookStore.service.BookSerive;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

@Service
public class BookServiceImpl implements BookSerive {

    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    @Autowired
    private BookReponsitory bookReponsitory;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryReponsitory categoryReponsitory;

    @Autowired
    PublisherRepository publisherRepository;

    @Override
    public List<BookEntity> loadAll() {
        return bookReponsitory.findAll();
    }

    @Override
    public BookEntity getByAliasName(String aliasName) {
        return bookReponsitory.getByAliasName(aliasName);
    }

    @Override
    public List<BookEntity> getTop5Book() {
        return bookReponsitory.getTop5Book(PageRequest.of(0, 4));
    }

    @Override
    public List<BookEntity> get10Books() {
        return bookReponsitory.get10Books(PageRequest.of(0, 10));
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookReponsitory.findAll(pageable);
    }

    @Override
    public Page<BookEntity> findBook(Pageable pageable, String name, String author, String price1, String price2) {
        if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(author) && StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getByNameAuthorAndPrice(pageable, name, author, Long.valueOf(price1), Long.valueOf(price2));
        }else if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(author)){
            return bookReponsitory.getByNameAuthor(pageable, name, author);
        }else if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getByNamePrice(pageable, name, Long.valueOf(price1), Long.valueOf(price2));
        }else if(StringUtils.isNotBlank(author) && StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getByAuthorPirce(pageable, author, Long.valueOf(price1), Long.valueOf(price2));
        }else if (StringUtils.isNotBlank(name)){
            return bookReponsitory.getByName(pageable, name);
        }else if(StringUtils.isNotBlank(author)){
            return bookReponsitory.getByAuthor(pageable, author);
        }else if(StringUtils.isNotBlank(price1) && StringUtils.isNotBlank(price2)){
            return bookReponsitory.getAllByPriceBetween(pageable, Long.valueOf(price1), Long.valueOf(price2));
        }
        return bookReponsitory.findAll(pageable);
    }

    @Override
    public BookCart getBook(Long id) {
        BookEntity bookEntity = bookReponsitory.getByDeletedAndId(false, id);
        BookCart bookCart = new BookCart(bookEntity, 1);
        return bookCart;
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookReponsitory.getByDeletedAndId(false, id);
    }

    @Override
    public Boolean addBook(AddBook addBook) {
        PublisherEntity publisher = publisherRepository.getOne(addBook.getPublisher().getId());
        CategoryEntity category = categoryReponsitory.getOne(addBook.getCategory().getId());
        BookEntity book = new BookEntity();
        book.setCategory(category);
        book.setPublisher(publisher);
        if(addBook.getAuthors() != null){
            book.setAuthors(new HashSet<>(addBook.getAuthors()));
        }
        book.setPrice(addBook.getPrice());
        book.setQuantity(addBook.getQuantity());
        book.setName(addBook.getBookName());
        book.setAliasName(addBook.getAliasName());
        book.setDescription(addBook.getDescription());
        book = bookReponsitory.save(book);
//         String UPLOADED_FOLDER = "file://" + System.getProperty("user.dir") + "/src/main/upload/";
        for (MultipartFile image: addBook.getImages()) {
            if(image != null){
                try {
                    byte[] bytes = image.getBytes();
                    Path path = Paths.get(ResourceConfig.FILE_PATH + image.getOriginalFilename());
                    Files.write(path, bytes);
                    ImageEntity img = new ImageEntity();
                    img.setBook(book);
                    img.setName(image.getOriginalFilename());
                    imageRepository.save(img);
                } catch (IOException ex) {
                    LOGGER.error(ex);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean checkBook(String alias) {
        BookEntity book = bookReponsitory.getByAliasName(alias);
        if(book != null){
            return true;
        }
        return false;
    }
}
