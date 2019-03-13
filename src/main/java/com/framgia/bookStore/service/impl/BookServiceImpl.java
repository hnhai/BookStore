package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.configuration.ResourceConfig;
import com.framgia.bookStore.entity.*;
import com.framgia.bookStore.form.AddBook;
import com.framgia.bookStore.form.BookCart;
import com.framgia.bookStore.form.EditBook;
import com.framgia.bookStore.repository.AuthorRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

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
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Override
    public List<BookEntity> loadAll() {
        return bookReponsitory.findAll();
    }

    @Override
    public BookEntity getByAliasName(String aliasName) {
        return bookReponsitory.getByAliasNameAndDeleted(aliasName, false);
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
        return bookReponsitory.getAllByDeleted(pageable, false);
    }

    @Override
    public BookCart getBook(Long id) {
        BookEntity bookEntity = bookReponsitory.getByDeletedAndId(false, id);
        if (bookEntity.getDiscount() != 0 && bookEntity.getDiscount() != null){
            bookEntity.setPrice(bookEntity.getPrice() - (bookEntity.getPrice() * bookEntity.getDiscount()/100));
        }
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
        book.setAliasName(addBook.getAliasName().toLowerCase());
        book.setDescription(addBook.getDescription());
        if(addBook.getDiscount() != null && addBook.getDiscount() > 0 && addBook.getDiscount() < 100){
            book.setDiscount(addBook.getDiscount());
        }
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

    @Override
    public BookEntity getById(Long id) {
        return bookReponsitory.getOne(id);
    }

    @Override
    @Transactional
    public Boolean editBook(EditBook editBook) {
        BookEntity book = bookReponsitory.getOne(editBook.getId());
        book.setName(editBook.getBookName());
        book.setAliasName(editBook.getAliasName().toLowerCase());
        book.setPublisher(editBook.getPublisher());
        book.setCategory(editBook.getCategory());
        book.setPrice(editBook.getPrice());
        book.setQuantity(editBook.getQuantity());
        book.setDescription(editBook.getDescription());
        if(editBook.getDiscount() != null && editBook.getDiscount() > 0 && editBook.getDiscount() < 100){
            book.setDiscount(editBook.getDiscount());
        }
        book = bookReponsitory.save(book);
        //Delete images
        if(!editBook.getRemoveImages().isEmpty()){
            for (String img: editBook.getRemoveImages()){
                imageRepository.deleteByNameAndAndBook(img.trim(), book);
                File file = new File(ResourceConfig.FILE_PATH + img);
                file.delete();
            }
        }
//        Add images
        for (MultipartFile image: editBook.getImages()) {
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
    public Boolean deleteBookByIds(List<Long> ids) {
        ids.forEach(id -> bookReponsitory.updateDeletedById(id));
        return true;
    }

    @Override
    public String importBook(List<BookEntity> books) {
        try {
            int i = 1;
            for (BookEntity book: books) {
                String alias = toSlug(book.getName());
                BookEntity bookCheck = bookReponsitory.getByAliasName(alias);
                if (bookCheck != null){
                    return "Book existed: " + book.getName() + " - Line: " + i;
                }
                i++;
                book.setAliasName(alias);
                book.setPublisher(getOrSavePublisher(book.getPublisher().getName()));
                book.setCategory(getOrSaveCategory(book.getCategory().getName()));
                Set<AuthorEnity> authors = new HashSet<>();
                authors.addAll(book.getAuthors());
                book.getAuthors().clear();
                for (AuthorEnity author: authors){
                    book.getAuthors().add(getOrSaveAuthor(author.getName().trim()));
                }
                bookReponsitory.save(book);
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "Import Success";
    }

    private PublisherEntity getOrSavePublisher(String name){
        PublisherEntity pub = publisherRepository.getByNameAndDeleted(name, false);
        if(pub == null){
            pub = publisherRepository.save(new PublisherEntity(name));
        }
        return pub;
    }

    private CategoryEntity getOrSaveCategory(String name){
        CategoryEntity cate = categoryReponsitory.getByNameAndDeleted(name, false);
        if (cate == null){
            cate = categoryReponsitory.save(new CategoryEntity(name));
        }
        return cate;
    }

    private AuthorEnity getOrSaveAuthor(String name){
        AuthorEnity author = authorRepository.getByNameAndDeleted(name, false);
        if (author == null){
            author = authorRepository.save(new AuthorEnity(name));
        }
        return author;
    }

    public String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
