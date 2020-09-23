package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.util.ConnectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoverRepositoryImpl implements CoverRepository {
    private final static Logger logger = LogManager.getLogger(CoverRepositoryImpl.class.getName());
    private static CoverRepositoryImpl instance;
    public static CoverRepositoryImpl getInstance() {
        if(instance == null){
            instance = new CoverRepositoryImpl();
        }
        return instance;
    }

    private static final String SQL_DELETE_COVERS_BY_BOOK_ID = "DELETE FROM Covers WHERE Book_id = ?;";
    private static final String SQL_ADD_COVER = "INSERT INTO Covers (Book_id, Title) VALUES (?, ?);";

    @Override
    public void add(int bookId, String title) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COVER)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.setString(index++, title);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.debug("Adding cover error!", e);
        }
    }

    @Override
    public void add(int bookId, String title, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COVER)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.setString(index++, title);
            preparedStatement.execute();
        }
    }

    @Override
    public void delete(int bookId) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COVERS_BY_BOOK_ID)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            logger.debug("Deleting cover error!", e);
        }
    }

    @Override
    public void delete(int bookId, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COVERS_BY_BOOK_ID)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.execute();
        }
    }
}
