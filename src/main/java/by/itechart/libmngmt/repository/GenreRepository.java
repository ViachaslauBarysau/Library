package by.itechart.libmngmt.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {
    void add(String title, Connection connection) throws SQLException;

    List<String> findAll(Connection connection) throws SQLException;

    List<Integer> getGenreIds(List<String> genres, Connection connection) throws SQLException;

    void addBookGenreRecord(int bookId, int genreId, Connection connection) throws SQLException;

    void deleteBooksGenresRecords(int bookId, Connection connection) throws SQLException;
}
