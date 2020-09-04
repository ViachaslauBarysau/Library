package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoverRepositoryImpl implements CoverRepository {
    private static CoverRepositoryImpl instance = new CoverRepositoryImpl();
    public static CoverRepositoryImpl getInstance() {
        return instance;
    }


    private static final String SQL_DELETE_COVERS_BY_BOOK_ID = "DELETE FROM Covers WHERE Book_id = ?;";
    private static final String SQL_ADD_COVER = "INSERT INTO Covers (Book_id, Title) VALUES (?, ?);";

    @Override
    public void add(int bookId, String title) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COVER)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.setString(2, title);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int bookId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COVERS_BY_BOOK_ID)) {
                preparedStatement.setInt(1, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
