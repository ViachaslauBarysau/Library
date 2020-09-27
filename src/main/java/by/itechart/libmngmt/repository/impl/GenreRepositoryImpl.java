package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.util.ConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreRepositoryImpl implements GenreRepository {
    private static final Logger LOGGER = LogManager.getLogger(GenreRepositoryImpl.class.getName());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
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

    @Override
    public void deleteBooksGenresRecords(int bookId) {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRES_RECORDS)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            LOGGER.debug("Deleting books_genres record error.", e);
        }
    }

    @Override
    public void deleteBooksGenresRecords(int bookId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRES_RECORDS)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.execute();
        }
    }

    @Override
    public List<Integer> getGenreIds(List<String> genres) {
        List<Integer> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
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
        } catch (SQLException e) {
            LOGGER.debug("Getting genres IDs record error.", e);
        }
        return resultList;
    }

    @Override
    public List<Integer> getGenreIds(List<String> genres, Connection connection) throws SQLException {
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

    @Override
    public void addBookGenreRecord(int bookId, int genreId) {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_GENRE_RECORD)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.setInt(index++, genreId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            LOGGER.debug("Adding books_genres record error.", e);
        }
    }

    @Override
    public void addBookGenreRecord(int bookId, int genreId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_GENRE_RECORD)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.setInt(index++, genreId);
            preparedStatement.execute();
        }
    }

    @Override
    public List<String> findAll() {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_TITLES)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(resultSet.getString("Title"));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting all genres error.", e);
        }
        return resultList;
    }

    @Override
    public List<String> findAll(Connection connection) throws SQLException {
        List<String> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_TITLES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("Title"));
            }
        }
        return resultList;
    }

    @Override
    public void add(String title) {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GENRE)) {
                int index = 1;
                preparedStatement.setString(index++, title);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            LOGGER.debug("Adding genre record error.", e);
        }
    }

    @Override
    public void add(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GENRE)) {
            int index = 1;
            preparedStatement.setString(index++, title);
            preparedStatement.execute();
        }
    }
}
