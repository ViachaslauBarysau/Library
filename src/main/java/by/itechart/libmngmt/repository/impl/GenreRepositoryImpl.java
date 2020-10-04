package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.GenreRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for CRUD operations with genres.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreRepositoryImpl implements GenreRepository {
    private static volatile GenreRepositoryImpl instance;

    public static synchronized GenreRepositoryImpl getInstance() {
        GenreRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (GenreRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GenreRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    private static final String SQL_GET_GENRES_TITLES = "SELECT Title FROM GENRES;";
    private static final String SQL_ADD_GENRE = "INSERT INTO Genres (Title) VALUES (?);";
    private static final String SQL_GET_GENRES_IDS = "SELECT ID FROM Genres WHERE Title = ANY (?);";
    private static final String SQL_INSERT_BOOK_GENRE_RECORD = "INSERT INTO Books_Genres (Book_Id, Genre_Id)" +
            " VALUES (?,?);";
    private static final String SQL_DELETE_BOOKS_GENRES_RECORDS = "DELETE FROM Books_Genres WHERE Book_id = ?;";

    /**
     * Deletes records in Books_Genres table by book ID.
     *
     * @param bookId     ID of book
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void deleteBooksGenresRecords(final int bookId, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRES_RECORDS)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.execute();
        }
    }

    /**
     * Returns list of genre's IDs searched by genre's titles.
     *
     * @param genres     list of genre's titles
     * @param connection current connection
     * @return list of genre's IDs
     * @throws SQLException in case of SQL failure
     */
    @Override
    public List<Integer> getGenreIds(final List<String> genres, final Connection connection) throws SQLException {
        List<Integer> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_IDS)) {
            Array genresArray = connection.createArrayOf("VARCHAR", genres.toArray());
            int index = 1;
            preparedStatement.setArray(index++, genresArray);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getInt("ID"));
            }
        }
        return resultList;
    }

    /**
     * Adds records in Books_Genres table.
     *
     * @param bookId     ID of book
     * @param genreId    ID of author
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void addBookGenreRecord(final int bookId, final int genreId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_GENRE_RECORD)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.setInt(index++, genreId);
            preparedStatement.execute();
        }
    }

    /**
     * Returns list of all genre's titles.
     *
     * @param connection current connection
     * @return list of genre's titles
     * @throws SQLException in case of SQL failure
     */
    @Override
    public List<String> findAll(final Connection connection) throws SQLException {
        List<String> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_TITLES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("Title"));
            }
        }
        return resultList;
    }

    /**
     * Adds genre's title to the database.
     *
     * @param title      genre's title
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void add(final String title, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GENRE)) {
            int index = 1;
            preparedStatement.setString(index++, title);
            preparedStatement.execute();
        }
    }
}
