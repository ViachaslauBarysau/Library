package by.itechart.libmngmt.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AuthorRepository {
    void add(String name, Connection connection) throws SQLException;

    List<String> findAll(Connection connection) throws SQLException;

    List<Integer> getAuthorIds(List<String> names, Connection connection) throws SQLException;

    void addBookAuthorRecord(int bookId, int authorId, Connection connection) throws SQLException;

    void deleteBooksAuthorsRecords(int bookId, Connection connection) throws SQLException;
}
