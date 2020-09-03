package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import javax.transaction.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {


    private static AuthorRepositoryImpl instance = new AuthorRepositoryImpl();
    public static AuthorRepositoryImpl getInstance() {
        return instance;
    }

    private static final String SQL_GET_AUTHORS_NAMES = "SELECT Name FROM Authors;";
    private static final String SQL_ADD_AUTHOR = "INSERT INTO Authors (Name) VALUES (?);";
    private static final String SQL_GET_AUTHORS_IDS = "SELECT ID FROM Authors WHERE Name = ANY (?);";
    private static final String SQL_INSERT_BOOK_AUTHOR_RECORD = "INSERT INTO Books_Authors (Book_Id, Author_Id) VALUES (?,?);";
    private static final String SQL_DELETE_BOOKS_AUTHORS_RECORDS = "DELETE FROM Books_Authors WHERE Book_id = ?;";

    @Override
    public void deleteBooksAuthorsRecords(int bookId) {

        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_AUTHORS_RECORDS)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(String name) {

        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_AUTHOR)) {
                preparedStatement.setString(1, name);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addBookAuthorRecord(int bookId, int authorId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_AUTHOR_RECORD)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.setInt(2, authorId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getId(Object[] names) {
        List<Integer> list = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AUTHORS_IDS)) {
                Array namesArray = connection.createArrayOf("VARCHAR", names);
                preparedStatement.setArray(1, namesArray);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list.add(resultSet.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public List<String> get() {
        List<String> list = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AUTHORS_NAMES)) {
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list.add(resultSet.getString("Name"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
