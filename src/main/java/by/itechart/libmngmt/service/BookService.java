package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookEntity> getBookPage(int pageNumber);
    int getPageCount();
    void delete(Object[] booksList);
    List<BookEntity> search(List<String> searchParams, int pageNumber);
    void addBook(BookEntity book);
    int addBookGetId(BookEntity book);
    BookDto find(int bookId);
    int getSearchPageCount(List<String> searchParams);
}
