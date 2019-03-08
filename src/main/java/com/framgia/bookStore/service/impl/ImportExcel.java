package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.AuthorEnity;
import com.framgia.bookStore.entity.BookEntity;
import com.framgia.bookStore.entity.CategoryEntity;
import com.framgia.bookStore.entity.PublisherEntity;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImportExcel {

    public static List<BookEntity> convertExcelToBook(MultipartFile file){
        HSSFWorkbook workbook;
        List<BookEntity> books = new ArrayList<>();
        try {
            workbook = new HSSFWorkbook(file.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            int i = 1;
            while (i <= worksheet.getLastRowNum()) {
                BookEntity book = new BookEntity();
                HSSFRow row = worksheet.getRow(i++);
                book.setName(row.getCell(1).getStringCellValue().trim());
                book.setPrice((long) row.getCell(2).getNumericCellValue());
                book.setQuantity((int) row.getCell(3).getNumericCellValue());
                PublisherEntity publisher = new PublisherEntity(row.getCell(4).getStringCellValue());
                book.setPublisher(publisher);
                CategoryEntity category = new CategoryEntity(row.getCell(5).getStringCellValue());
                book.setCategory(category);
                String authors [] = row.getCell(6).getStringCellValue().split(",");
                for (int j = 0; j < authors.length; j++){
                    book.getAuthors().add(new AuthorEnity(authors[j]));
                }
                books.add(book);
            }
            workbook.close();
        } catch (IOException e) {
            return null;
        }
        return books;
    }
}
