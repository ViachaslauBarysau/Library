package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.dto.BookAddDto;
import by.itechart.libmngmt.entity.Book;
import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {

    private static GenreRepositoryImpl instance = new GenreRepositoryImpl();
    public static GenreRepositoryImpl getInstance() {
        return instance;
    }

    private static final String SQL_GET_GENRES_TITLES = "SELECT Title FROM GENRES;";

    @Override
    public List<String> get() {
        List<String> list = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_GENRES_TITLES)) {
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list.add(resultSet.getString("Title"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void add(BookAddDto bookAddDto) {

    }
}
