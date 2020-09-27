package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.util.ConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoverRepositoryImpl implements CoverRepository {
    private static final Logger LOGGER = LogManager.getLogger(CoverRepositoryImpl.class.getName());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static volatile CoverRepositoryImpl instance;

    public static synchronized CoverRepositoryImpl getInstance() {
        CoverRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (CoverRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CoverRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    private static final String SQL_DELETE_COVERS_BY_BOOK_ID = "DELETE FROM Covers WHERE Book_id = ?;";
    private static final String SQL_ADD_COVER = "INSERT INTO Covers (Book_id, Title) VALUES (?, ?);";

    @Override
    public void add(int bookId, String title) {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COVER)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.setString(index++, title);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            LOGGER.debug("Adding cover error.", e);
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
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COVERS_BY_BOOK_ID)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            LOGGER.debug("Deleting cover error.", e);
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
