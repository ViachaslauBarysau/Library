package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.util.ConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for CRUD operations with reader cards.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderCardRepositoryImpl implements ReaderCardRepository {
    private static final Logger LOGGER = LogManager.getLogger(ReaderCardRepositoryImpl.class.getName());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static volatile ReaderCardRepositoryImpl instance;

    public static synchronized ReaderCardRepositoryImpl getInstance() {
        ReaderCardRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderCardRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderCardRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    private static final String SQL_GET_READER_CARDS_BY_BOOK_ID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.Reader_ID WHERE Book_id = ?;";
    private static final String SQL_GET_ACTIVE_READER_CARDS_BY_BOOK_ID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.Reader_ID WHERE Book_id = ? AND Return_date IS NULL;";
    private static final String SQL_UPDATE_READER_CARD = "UPDATE Books_Readers SET Status = ?, Return_date = ?," +
            " Comment = ? WHERE ReaderCard_ID = ?;";
    private static final String SQL_ADD_READER_CARD = "INSERT INTO Books_Readers(Book_ID, Reader_ID," +
            " Borrow_date, Time_Period, Status, Due_date, Comment) VALUES (?,?,?,?,?,?,?);";
    private static final String SQL_GET_READER_CARD_BY_ID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.Reader_ID WHERE ReaderCard_ID = ?;";
    private static final String SQL_GET_NEAREST_RETURN_DATE = "SELECT Due_date FROM Books_Readers WHERE" +
            " Book_id = ? AND Due_date > NOW() AND Return_date IS NULL ORDER BY Due_date ASC LIMIT 1;";
    public static final String SQL_GET_EXPIRING_READER_CARDS = "SELECT Books.Title, Readers.Name, Readers.Email" +
            " FROM Books LEFT JOIN Books_Readers ON Books.Id=Books_Readers.Book_Id" +
            " LEFT JOIN Readers ON Books_Readers.Reader_Id=Readers.Id WHERE Due_Date - CURRENT_DATE = ?" +
            " AND Return_Date IS NULL;";

    /**
     * Returns list of expiring reader cards(some days before/after expiring).
     *
     * @param days list of genre's titles
     * @return list ReaderCardEntities
     */
    @Override
    public List<ReaderCardEntity> getExpiringReaderCards(final int days) {
        List<ReaderCardEntity> readerCardEntities = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_EXPIRING_READER_CARDS)) {
            int index = 1;
            preparedStatement.setInt(index++, days);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                readerCardEntities.add(ReaderCardEntity.builder()
                        .bookTitle(resultSet.getString("Title"))
                        .readerName(resultSet.getString("Name"))
                        .readerEmail(resultSet.getString("Email"))
                        .build());
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting expired reader cards error.", e);
        }
        return readerCardEntities;
    }

    /**
     * Returns nearest available date of book if it's unavailable.
     *
     * @param bookId ID of book
     * @return Date object
     */
    @Override
    public Date getNearestReturnDate(final int bookId) {
        Date resultDate = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_NEAREST_RETURN_DATE)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultDate = resultSet.getDate("Due_date");
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting nearest available date error.", e);
        }
        return resultDate;
    }

    /**
     * Adds reader card record to the database.
     *
     * @param readerCard ReaderCard object
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void add(final ReaderCardEntity readerCard, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_READER_CARD)) {
            int index = 1;
            preparedStatement.setInt(index++, readerCard.getBookId());
            preparedStatement.setInt(index++, readerCard.getReaderId());
            preparedStatement.setDate(index++, readerCard.getBorrowDate());
            preparedStatement.setInt(index++, readerCard.getTimePeriod());
            preparedStatement.setString(index++, readerCard.getStatus());
            preparedStatement.setDate(index++, readerCard.getDueDate());
            preparedStatement.setString(index++, readerCard.getComment());
            preparedStatement.execute();
        }
    }

    /**
     * Updates existing reader card record.
     *
     * @param readerCard ReaderCard object
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void update(final ReaderCardEntity readerCard, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_READER_CARD)) {
            int index = 1;
            preparedStatement.setString(index++, readerCard.getStatus());
            preparedStatement.setTimestamp(index++, readerCard.getReturnDate());
            preparedStatement.setString(index++, readerCard.getComment());
            preparedStatement.setInt(index++, readerCard.getId());
            preparedStatement.execute();
        }
    }

    /**
     * Returns reader card searched by its ID.
     *
     * @param readerCardId ID of reader card
     * @return ReaderCardEntity object
     */
    @Override
    public ReaderCardEntity getReaderCard(final int readerCardId) {
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READER_CARD_BY_ID)) {
            int index = 1;
            preparedStatement.setInt(index++, readerCardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                readerCardEntity = ReaderCardEntity.builder()
                        .id(resultSet.getInt("ReaderCard_ID"))
                        .bookId(resultSet.getInt("book_ID"))
                        .readerId(resultSet.getInt("reader_ID"))
                        .readerName(resultSet.getString("Name"))
                        .readerEmail(resultSet.getString("Email"))
                        .borrowDate(resultSet.getDate("Borrow_date"))
                        .timePeriod(resultSet.getInt("Time_Period"))
                        .status(resultSet.getString("Status"))
                        .dueDate(resultSet.getDate("Due_date"))
                        .returnDate(resultSet.getTimestamp("Return_date"))
                        .comment(resultSet.getString("Comment"))
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader card error.", e);
        }
        return readerCardEntity;
    }

    /**
     * Returns list of active reader cards searched by book ID.
     *
     * @param bookId ID of book
     * @return list of ReaderCardEntity
     */
    @Override
    public List<ReaderCardEntity> getActiveReaderCards(final int bookId) {
        List<ReaderCardEntity> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ACTIVE_READER_CARDS_BY_BOOK_ID)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(ReaderCardEntity.builder()
                        .id(resultSet.getInt("ReaderCard_ID"))
                        .bookId(resultSet.getInt("book_ID"))
                        .readerId(resultSet.getInt("reader_ID"))
                        .readerName(resultSet.getString("Name"))
                        .readerEmail(resultSet.getString("Email"))
                        .borrowDate(resultSet.getDate("Borrow_date"))
                        .timePeriod(resultSet.getInt("Time_Period"))
                        .status(resultSet.getString("Status"))
                        .dueDate(resultSet.getDate("Due_date"))
                        .returnDate(resultSet.getTimestamp("Return_date"))
                        .comment(resultSet.getString("Comment"))
                        .build());
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader cards error.", e);
        }
        return resultList;
    }

    /**
     * Returns list of all reader cards searched by book ID.
     *
     * @param bookId ID of book
     * @return list of ReaderCardEntity
     */
    @Override
    public List<ReaderCardEntity> getAllReaderCards(final int bookId) {
        List<ReaderCardEntity> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READER_CARDS_BY_BOOK_ID)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(ReaderCardEntity.builder()
                        .id(resultSet.getInt("ReaderCard_ID"))
                        .bookId(resultSet.getInt("book_ID"))
                        .readerId(resultSet.getInt("reader_ID"))
                        .readerName(resultSet.getString("Name"))
                        .readerEmail(resultSet.getString("Email"))
                        .borrowDate(resultSet.getDate("Borrow_date"))
                        .timePeriod(resultSet.getInt("Time_Period"))
                        .status(resultSet.getString("Status"))
                        .dueDate(resultSet.getDate("Due_date"))
                        .returnDate(resultSet.getTimestamp("Return_date"))
                        .comment(resultSet.getString("Comment"))
                        .build());
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting reader cards error.", e);
        }
        return resultList;
    }
}
