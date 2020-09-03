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

//    private static final String SQL_GET_BOOK_COVERS = "SELECT Title FROM Covers WHERE Book_ID = ?;";
//    private static final String SQL_GET_AUTHORS_IDS = "SELECT ID FROM Covers WHERE ID = ANY (?);";
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
    public List<String> get() {
        List<String> list = new ArrayList<>();
//        try (Connection connection = ConnectionHelper.getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BOOK_COVERS)) {
//                final ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    list.add(resultSet.getString("Title"));
//                }
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return list;
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

    @Override
    public List<Integer> getId(Object[] names) {
        List<Integer> list = new ArrayList<>();
//        try (Connection connection = ConnectionHelper.getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AUTHORS_IDS)) {
//                Array namesArray = connection.createArrayOf("VARCHAR", names);
//                preparedStatement.setArray(1, namesArray);
//                final ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    list.add(resultSet.getInt("ID"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return list;
    }

}
