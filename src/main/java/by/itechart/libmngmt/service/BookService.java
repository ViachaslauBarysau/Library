package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookService {
    List<BookDto> getBookPage(int pageNumber);
    List<BookDto> getAvailableBookPage(int pageNumber);
    int getAvailableBookPageCount();
    int getPageCount();
    void delete(List<Integer> booksList);
    List<BookDto> search(List<String> searchParams, int pageNumber);
    int addEditBook(BookDto book);
    int addBookGetId(BookDto book);
    BookDto find(int bookId);
    int getSearchPageCount(List<String> searchParams);
    void updateBook(BookDto book);
    int addBookGetId(BookDto bookDto, Connection connection) throws SQLException;
    void updateBook(BookDto bookDto, Connection connection) throws SQLException;
}
