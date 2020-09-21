package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {

    private static AuthorRepositoryImpl instance;
    public static AuthorRepositoryImpl getInstance() {
        if(instance == null){
            instance = new AuthorRepositoryImpl();
        }
        return instance;
    }

    private static final String SQL_GET_AUTHORS_NAMES = "SELECT Name FROM Authors;";
    private static final String SQL_ADD_AUTHOR = "INSERT INTO Authors (Name) VALUES (?);";
    private static final String SQL_GET_AUTHORS_IDS = "SELECT ID FROM Authors WHERE Name = ANY (?);";
    private static final String SQL_INSERT_BOOK_AUTHOR_RECORD = "INSERT INTO Books_Authors (Book_Id," +
            " Author_Id) VALUES (?,?);";
    private static final String SQL_DELETE_BOOKS_AUTHORS_RECORDS = "DELETE FROM Books_Authors WHERE Book_id = ?;";

    @Override
    public void add(String name) {

        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_AUTHOR)) {
                int index = 1;
                preparedStatement.setString(index++, name);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(String name, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_AUTHOR)) {
            int index = 1;
            preparedStatement.setString(index++, name);
            preparedStatement.execute();
        }
    }

    @Override
    public void deleteBooksAuthorsRecords(int bookId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_AUTHORS_RECORDS)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBooksAuthorsRecords(int bookId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_AUTHORS_RECORDS)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.execute();
        }
    }

    @Override
    public void addBookAuthorRecord(int bookId, int authorId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_AUTHOR_RECORD)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.setInt(index++, authorId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookAuthorRecord(int bookId, int authorId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_AUTHOR_RECORD)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.setInt(index++, authorId);
            preparedStatement.execute();
        }
    }

    @Override
    public List<Integer> getId(List<String> names) {
        List<Integer> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AUTHORS_IDS)) {
                Array namesArray = connection.createArrayOf("VARCHAR", names.toArray());
                int index = 1;
                preparedStatement.setArray(index++, namesArray);
                ResultSet resultSet = preparedStatement.executeQuery();
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
    public List<Integer> getId(List<String> names, Connection connection) throws SQLException {
        List<Integer> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AUTHORS_IDS)) {
            Array namesArray = connection.createArrayOf("VARCHAR", names.toArray());
            int index = 1;
            preparedStatement.setArray(index++, namesArray);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getInt("ID"));
            }
        }
        return resultList;
    }

    @Override
    public List<String> findAll() {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AUTHORS_NAMES)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(resultSet.getString("Name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<String> findAll(Connection connection) throws SQLException {
        List<String> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AUTHORS_NAMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("Name"));
            }
        }
        return resultList;
    }
}