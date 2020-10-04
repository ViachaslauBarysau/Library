package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.repository.CoverRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Provides methods for CRUD operations with covers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoverRepositoryImpl implements CoverRepository {
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

    /**
     * Adds book's cover title to the database.
     *
     * @param bookId     book ID
     * @param title      cover's title
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void add(final int bookId, String title, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COVER)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.setString(index++, title);
            preparedStatement.execute();
        }
    }

    /**
     * Deletes book's cover title from database.
     *
     * @param bookId     book ID
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void delete(final int bookId, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COVERS_BY_BOOK_ID)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            preparedStatement.execute();
        }
    }
}
