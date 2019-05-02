package com.framgia.bookStore.form;

import java.io.Serializable;

public class BookSearch implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Long price;
    private String category;

    public BookSearch(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
