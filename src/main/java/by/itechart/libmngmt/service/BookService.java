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
    void addEditBook(BookDto book);
    int addBookGetId(BookDto book);
    BookDto find(int bookId);
    int getSearchPageCount(List<String> searchParams);
    void updateBook(BookDto bookDto);
    int addBookGetId(BookDto book, Connection connection) throws SQLException;
    void updateBook(BookDto bookDto, Connection connection) throws SQLException;
}
