package by.itechart.libmngmt.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AuthorRepository {
    void add(String name);
    List<String> get();
    List<Integer> getId(List<String> names);
    void addBookAuthorRecord(int bookId, int authorId);
    void deleteBooksAuthorsRecords(int bookId);

    void add(String name, Connection connection) throws SQLException;
    List<String> get(Connection connection) throws SQLException;
    List<Integer> getId(List<String> names, Connection connection) throws SQLException;
    void addBookAuthorRecord(int bookId, int authorId, Connection connection) throws SQLException;
    void deleteBooksAuthorsRecords(int bookId, Connection connection) throws SQLException;

}
