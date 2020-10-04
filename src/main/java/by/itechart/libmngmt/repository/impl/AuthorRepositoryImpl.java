package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.AuthorRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for CRUD operations with authors.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorRepositoryImpl implements AuthorRepository {
    private static volatile AuthorRepositoryImpl instance;

    public static synchronized AuthorRepositoryImpl getInstance() {
        AuthorRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (AuthorRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AuthorRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    private static final String SQL_GET_AUTHORS_NAMES = "SELECT Name FROM Authors;";
    private static final String SQL_ADD_AUTHOR = "INSERT INTO Authors (Name) VALUES (?);";
    private static final String SQL_GET_AUTHORS_IDS = "SELECT ID FROM Authors WHERE Name = ANY (?);";
    private static final String SQL_INSERT_BOOK_AUTHOR_RECORD = "INSERT INTO Books_Authors (Book_Id," +
            " Author_Id) VALUES (?,?);";
    private static final String SQL_DELETE_BOOKS_AUTHORS_RECORDS = "DELETE FROM Books_Authors WHERE Book_id = ?;";

    /**
     * Adds author's names to the database.
     *
     * @param name       author's name
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void add(final String name, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_AUTHOR)) {
            int index = 1;
            preparedStatement.setString(index++, name);
            preparedStatement.execute();
        }
    }

    /**
     * Deletes records in Books_Authors table.
     *
     * @param bookId     ID of book
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void deleteBooksAuthorsRecords(final int bookId, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_AUTHORS_RECORDS)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.execute();
        }
    }

    /**
     * Adds records in Books_Authors table.
     *
     * @param bookId     ID of book
     * @param authorId   ID of author
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void addBookAuthorRecord(final int bookId, final int authorId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK_AUTHOR_RECORD)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.setInt(index++, authorId);
            preparedStatement.execute();
        }
    }

    /**
     * Returns list of author's IDs searched by author's names.
     *
     * @param names      list of authors names
     * @param connection current connection
     * @return list of author's IDs
     * @throws SQLException in case of SQL failure
     */
    @Override
    public List<Integer> getAuthorIds(final List<String> names, final Connection connection) throws SQLException {
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

    /**
     * Returns list of all author's names.
     *
     * @param connection current connection
     * @return list of author's names
     * @throws SQLException in case of SQL failure
     */
    @Override
    public List<String> findAll(final Connection connection) throws SQLException {
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