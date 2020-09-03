package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookEntity> getPageOfBooks (int pageNumber);
    int getCountOfPages();
    void deleteBooks(Object[] booksList);
    BookEntity getBook(int bookId);
    List<BookEntity> searchBooks(List<String> searchParams);
    void addBook(BookEntity book);
    int add(BookEntity book);
    BookDto find(int bookId);
}
