package com.framgia.bookStore.form;

import com.framgia.bookStore.entity.BookEntity;

import java.io.Serializable;

public class BookCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private BookEntity book;
    private int quantity;

    public BookCart(BookEntity book, int quantiy) {
        this.book = book;
        this.quantity = quantiy;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
