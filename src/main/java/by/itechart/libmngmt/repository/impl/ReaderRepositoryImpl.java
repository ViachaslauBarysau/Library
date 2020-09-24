package by.itechart.libmngmt.repository.impl;


import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.util.ConnectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class  ReaderRepositoryImpl implements ReaderRepository {
    private final static Logger LOGGER = LogManager.getLogger(ReaderCardRepositoryImpl.class.getName());
    private static ReaderRepositoryImpl instance;
    public static synchronized ReaderRepositoryImpl getInstance() {
        if(instance == null){
            instance = new ReaderRepositoryImpl();
        }
        return instance;
    }

    private static final String SQL_ADD_READER = "INSERT INTO Readers (Name, Email) VALUES (?,?);";
    private static final String SQL_GET_READERS_EMAILS = "SELECT Email FROM Readers WHERE Email LIKE ?;";
    private static final String SQL_GET_READER_NAME_BY_EMAIL = "SELECT NAME FROM Readers WHERE Email = ?;";
    public static final String SQL_INSERT_OR_UPDATE_READER = "INSERT INTO Readers (Email, Name) VALUES(?, ?)" +
            " ON DUPLICATE KEY UPDATE Name = VALUES(name); ";
    public static final String SQL_GET_READER_ID_BY_EMAIL = "SELECT ID FROM READERS WHERE EMAIL = ?;";

    @Override
    public void insertUpdate (ReaderEntity readerEntity) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_OR_UPDATE_READER)) {
                int index = 1;
                preparedStatement.setString(index++, readerEntity.getEmail());
                preparedStatement.setString(index++, readerEntity.getName());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            LOGGER.debug("Insert/update reader error.", e);
        }
    }

    @Override
    public int getId(String email) {
        int readerId = 0;
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READER_ID_BY_EMAIL)) {
                int index = 1;
                preparedStatement.setString(index++, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    readerId = resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader ID error.", e);
        }
        return readerId;
    }

    @Override
    public String getName(String email) {
        String name = "";
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READER_NAME_BY_EMAIL)) {
                int index = 1;
                preparedStatement.setString(index++, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    name = resultSet.getString("Name");
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader name error.", e);
        }
        return name;
    }

    @Override
    public List<String> getEmails(String searchParameter) {
        List<String> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERS_EMAILS)) {
                int index = 1;
                preparedStatement.setString(index++, searchParameter);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                        resultList.add(resultSet.getString("Email"));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader emails error.", e);
        }
        return resultList;
    }

    @Override
    public void add(ReaderEntity reader) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_READER)) {
                int index = 1;
                preparedStatement.setString(index++, reader.getName());
                preparedStatement.setString(index++, reader.getEmail());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            LOGGER.debug("Adding reader error.", e);
        }
    }
}
