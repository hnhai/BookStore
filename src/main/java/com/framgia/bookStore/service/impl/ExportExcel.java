package com.framgia.bookStore.service.impl;

import com.framgia.bookStore.entity.BookEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportExcel extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment;filename=\"books.xls\"");
        List<BookEntity> books = (List<BookEntity>) model.get("books");
        Sheet sheet = workbook.createSheet("List Book");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Book ID");
        header.createCell(1).setCellValue("Book Name");
        header.createCell(2).setCellValue("Price");
        header.createCell(3).setCellValue("Quantity");
        header.createCell(4).setCellValue("Publisher");
        header.createCell(5).setCellValue("Category");
        header.createCell(6).setCellValue("Authors");
        int rowNum = 1;
        for (BookEntity book : books) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(book.getId());
            row.createCell(1).setCellValue(book.getName());
            row.createCell(2).setCellValue(book.getPrice());
            row.createCell(3).setCellValue(book.getQuantity());
            row.createCell(4).setCellValue(book.getPublisher().getName());
            row.createCell(5).setCellValue(book.getCategory().getName());
            // Get author's name to add to SET
            Set<String> authors = new LinkedHashSet<>();
            book.getAuthors().forEach(a -> authors.add(a.getName()));
            row.createCell(6).setCellValue(String.join(", ", authors));
        }
    }
}
