package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookService {
    List<BookEntity> getBookPage(int pageNumber);
    int getPageCount();
    void delete(Object[] booksList);
    List<BookEntity> search(List<String> searchParams, int pageNumber);
    int addEditBook(BookEntity book);
    int addBookGetId(BookEntity book);
    BookDto find(int bookId);
    int getSearchPageCount(List<String> searchParams);
    void updateBook(BookEntity book);
    int addBookGetId(BookEntity book, Connection connection) throws SQLException;
    void updateBook(BookEntity bookDto, Connection connection) throws SQLException;
}
