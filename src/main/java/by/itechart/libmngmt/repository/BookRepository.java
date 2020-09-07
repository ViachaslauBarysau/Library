package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    List<BookEntity> get(int limitOffset);
    int getPageCount();
    void delete(Object[] bookList);
    BookEntity find(int bookId);
    List<BookEntity> search(List<String> searchParams, int limitOffset);
    int add(BookDto book);
    void update(BookDto book);
    int getSearchPageCount(List<String> searchParams);
    int add(BookDto book, Connection connection) throws SQLException;
    void update(BookDto book, Connection connection) throws SQLException;
}
