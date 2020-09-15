package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {

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
    public void deleteBooksGenresRecords(int bookId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRES_RECORDS)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBooksGenresRecords(int bookId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_GENRES_RECORDS)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.execute();
        }
    }

    @Override
    public List<Integer> getId(Object[] genres) {
        List<Integer> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_IDS)) {
                Array genresArray = connection.createArrayOf("VARCHAR", genres);
                preparedStatement.setArray(1, genresArray);
                preparedStatement.executeQuery();
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(resultSet.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<Integer> getId(Object[] genres, Connection connection) throws SQLException {
        List<Integer> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_IDS)) {
            Array genresArray = connection.createArrayOf("VARCHAR", genres);
            preparedStatement.setArray(1, genresArray);
            preparedStatement.executeQuery();
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getInt("ID"));
            }
        }
        return resultList;
    }

    @Override
    public void addBookGenreRecord(int bookId, int genreId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_GENRE_RECORD)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.setInt(2, genreId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookGenreRecord(int bookId, int genreId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_GENRE_RECORD)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, genreId);
            preparedStatement.execute();
        }
    }

    @Override
    public List<String> get() {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_TITLES)) {
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(resultSet.getString("Title"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<String> get(Connection connection) throws SQLException {
        List<String> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_TITLES)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
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
                preparedStatement.setString(1, title);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GENRE)) {
            preparedStatement.setString(1, title);
            preparedStatement.execute();
        }
    }

}
