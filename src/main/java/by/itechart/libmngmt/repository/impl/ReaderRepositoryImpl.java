package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.entity.Book;
import by.itechart.libmngmt.entity.Reader;
import by.itechart.libmngmt.entity.ReaderCard;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReaderRepositoryImpl implements ReaderRepository {

    public static ReaderRepositoryImpl getInstance() {
        return instance;
    }

    private static ReaderRepositoryImpl instance = new ReaderRepositoryImpl();

    private static final String SQL_GET_READERS_BY_BOOKID = "SELECT * FROM Readers LEFT JOIN BOOKS_READERS ON Readers.ID=Books_Readers.Reader_Id WHERE Books_Readers.Book_id = ?;";

    @Override
    public Map<Integer, Reader> get(int bookId) {
        Map<Integer, Reader> map = new HashMap<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERS_BY_BOOKID)) {
                preparedStatement.setInt(1, bookId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    map.put(resultSet.getInt("ID"), Reader.builder()
                                                .id(resultSet.getInt("ID"))
                                                .name(resultSet.getString("Name"))
                                                .email(resultSet.getString("Email"))
                                                .dateOfRegistration(resultSet.getDate("Date_of_registration"))
                                                .phoneNumber(resultSet.getLong("Phone_number"))
                                                .build());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
