package by.itechart.libmngmt.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {
    void add(String title);
    List<String> get();
    List<Integer> getId(List<String> genres);
    void addBookGenreRecord(int bookId, int genreId);
    void deleteBooksGenresRecords(int bookId);

    void add(String title, Connection connection) throws SQLException;
    List<String> get(Connection connection) throws SQLException;
    List<Integer> getId(List<String> genres, Connection connection) throws SQLException;
    void addBookGenreRecord(int bookId, int genreId, Connection connection) throws SQLException;
    void deleteBooksGenresRecords(int bookId, Connection connection) throws SQLException;
}
