package com.framgia.bookStore.dao;

import com.framgia.bookStore.form.BookSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookDAO {
    Page<BookSearch> getBookByName(String bookName, Pageable pageable);
}
