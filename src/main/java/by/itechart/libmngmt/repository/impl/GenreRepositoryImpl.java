package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.util.ConnectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {
    private final static Logger logger = LogManager.getLogger(GenreRepositoryImpl.class.getName());
    private static GenreRepositoryImpl instance;
    public static GenreRepositoryImpl getInstance() {
        if(instance == null){
            instance = new GenreRepositoryImpl();
        }
        return instance;
    }

    private static final String SQL_GET_GENRES_TITLES = "SELECT Title FROM GENRES;";
    private static final String SQL_ADD_GENRE = "INSERT INTO Genres (Title) VALUES (?);";
    private static final String SQL_GET_GENRES_IDS = "SELECT ID FROM Genres WHERE Title = ANY (?);";
    private static final String SQL_INSERT_BOOK_GENRE_RECORD = "INSERT INTO Books_Genres (Book_Id, Genre_Id)" +
            " VALUES (?,?);";
    private static final String SQL_DELETE_BOOKS_GENRES_RECORDS = "DELETE FROM Books_Genres WHERE Book_id = ?;";

    @Override
    public void deleteBooksGenres(int bookId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRES_RECORDS)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.debug("Deleting books_genres record error!", e);
        }
    }

    @Override
    public void deleteBooksGenres(int bookId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRES_RECORDS)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.execute();
        }
    }

    @Override
    public List<Integer> getId(List<String> genres) {
        List<Integer> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
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
            logger.debug("Getting genres IDs record error!", e);
        }
        return resultList;
    }

    @Override
    public List<Integer> getId(List<String> genres, Connection connection) throws SQLException {
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
    public void addBookGenre(int bookId, int genreId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_GENRE_RECORD)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.setInt(index++, genreId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.debug("Adding books_genres record error!", e);
        }
    }

    @Override
    public void addBookGenre(int bookId, int genreId, Connection connection) throws SQLException {
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
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_TITLES)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(resultSet.getString("Title"));
                }
            }
        } catch (SQLException e) {
            logger.debug("Getting all genres error!", e);
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
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GENRE)) {
                int index = 1;
                preparedStatement.setString(index++, title);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.debug("Adding genre record error!", e);
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
