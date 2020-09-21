package by.itechart.libmngmt.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {
    void add(String title);
    List<String> findAll();
    List<Integer> getId(List<String> genres);
    void addBookGenre(int bookId, int genreId);
    void deleteBooksGenres(int bookId);
    void add(String title, Connection connection) throws SQLException;
    List<String> findAll(Connection connection) throws SQLException;
    List<Integer> getId(List<String> genres, Connection connection) throws SQLException;
    void addBookGenre(int bookId, int genreId, Connection connection) throws SQLException;
    void deleteBooksGenres(int bookId, Connection connection) throws SQLException;
}
