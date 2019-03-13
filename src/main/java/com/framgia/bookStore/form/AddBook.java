package com.framgia.bookStore.form;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.entity.CategoryEntity;
import com.framgia.bookStore.entity.PublisherEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AddBook {
    private String bookName;
    private String aliasName;
    private CategoryEntity category;
    private PublisherEntity publisher;
    private Long price;
    private Long discount;
    private Integer quantity;
    private String description;
    private List<AuthorEnity> authors;
    private MultipartFile[] images;

    public AddBook() {
    }

    public AddBook(String bookName, CategoryEntity category, PublisherEntity publisher, Long price, Integer quantity, String description, MultipartFile[] images) {
        this.bookName = bookName;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.images = images;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }

    public List<AuthorEnity> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorEnity> authors) {
        this.authors = authors;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }
}
