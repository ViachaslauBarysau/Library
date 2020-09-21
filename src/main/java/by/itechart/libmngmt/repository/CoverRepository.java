package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.BookDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CoverRepository {
    void add(int bookId, String title);
    void delete(int bookId);
    void add(int bookId, String title, Connection connection) throws SQLException;
    void delete(int bookId, Connection connection) throws SQLException;
}
