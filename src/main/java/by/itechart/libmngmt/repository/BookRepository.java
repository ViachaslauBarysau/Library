package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    List<BookEntity> findAll(int offset);
    int getPageCount();
    void delete(Object[] bookList);
    BookEntity find(int bookId);
    List<BookEntity> search(List<String> searchParams, int limitOffset);
    int add(BookEntity book);
    void update(BookEntity book);
    int getSearchPageCount(List<String> searchParams);
    int add(BookEntity book, Connection connection) throws SQLException;
    void update(BookEntity book, Connection connection) throws SQLException;
}
