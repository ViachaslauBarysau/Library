package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.util.ConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for CRUD operations with readers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderRepositoryImpl implements ReaderRepository {
    private static final Logger LOGGER = LogManager.getLogger(ReaderCardRepositoryImpl.class.getName());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static volatile ReaderRepositoryImpl instance;

    public static synchronized ReaderRepositoryImpl getInstance() {
        ReaderRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    private static final String SQL_GET_READERS_EMAILS = "SELECT Email FROM Readers WHERE Email LIKE ?;";
    private static final String SQL_GET_READER_NAME_BY_EMAIL = "SELECT NAME FROM Readers WHERE Email = ?;";
    public static final String SQL_INSERT_OR_UPDATE_READER = "INSERT INTO Readers (Email, Name) VALUES(?, ?)" +
            " ON DUPLICATE KEY UPDATE Name = VALUES(name); ";
    public static final String SQL_GET_READER_ID_BY_EMAIL = "SELECT ID FROM READERS WHERE EMAIL = ?;";

    /**
     * Makes and adding new/updating existing reader into the database.
     *
     * @param readerEntity list of book's IDs for deleting
     * @param connection   current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void insertUpdate(final ReaderEntity readerEntity, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_OR_UPDATE_READER)) {
            int index = 1;
            preparedStatement.setString(index++, readerEntity.getEmail());
            preparedStatement.setString(index++, readerEntity.getName());
            preparedStatement.execute();
        }
    }

    /**
     * Returns ID of reader searched by email.
     *
     * @param email      reader's email
     * @param connection current connection
     * @return reader's ID
     * @throws SQLException in case of SQL failure
     */
    @Override
    public int getId(final String email, final Connection connection) throws SQLException {
        int readerId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READER_ID_BY_EMAIL)) {
            int index = 1;
            preparedStatement.setString(index++, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                readerId = resultSet.getInt("ID");
            }
        }
        return readerId;
    }

    /**
     * Returns name of reader searched by email.
     *
     * @param email reader's email
     * @return reader's name
     */
    @Override
    public String getName(final String email) {
        String name = "";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READER_NAME_BY_EMAIL)) {
            int index = 1;
            preparedStatement.setString(index++, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("Name");
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader name error.", e);
        }
        return name;
    }

    /**
     * Returns list of reader's emails searched by email pattern.
     *
     * @param searchParameter email pattern
     * @return list of reader's emails
     */
    @Override
    public List<String> getEmails(final String searchParameter) {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERS_EMAILS)) {
            int index = 1;
            preparedStatement.setString(index++, searchParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("Email"));
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader emails error.", e);
        }
        return resultList;
    }
}
