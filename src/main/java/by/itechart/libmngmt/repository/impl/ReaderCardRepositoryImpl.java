package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.util.ConnectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderCardRepositoryImpl implements ReaderCardRepository {
    private final static Logger logger = LogManager.getLogger(ReaderCardRepositoryImpl.class.getName());
    private static ReaderCardRepositoryImpl instance;
    public static ReaderCardRepositoryImpl getInstance() {
        if(instance == null){
            instance = new ReaderCardRepositoryImpl();
        }
        return instance;
    }

    private static final String SQL_GET_READERCARDS_BY_BOOKID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.Reader_ID WHERE Book_id = ?;";
    private static final String SQL_UPDATE_READER_CARD = "UPDATE Books_Readers SET Status = ?, Return_date = ?, Comment = ? WHERE ReaderCard_ID = ?;";
    private static final String SQL_ADD_READER_CARD = "INSERT INTO Books_Readers(Book_ID, Reader_ID," +
            " Borrow_date, Time_Period, Status, Due_date, Comment) VALUES (?,?,?,?,?,?,?);";
    private static final String SQL_GET_READERCARD_BY_ID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.Reader_ID WHERE ReaderCard_ID = ?;";
    private static final String SQL_GET_NEAREST_RETURN_DATE = "SELECT Due_date FROM Books_Readers WHERE" +
            " Book_id = ? AND Due_date > NOW() AND Return_date IS NULL ORDER BY Due_date ASC LIMIT 1;";
    private static final String SQL_GET_ACTIVE_READER_CARD_COUNT_BY_BOOK_ID = "SELECT COUNT(ReaderCard_Id)" +
            " FROM Books_Readers WHERE Book_Id = ? AND Return_date IS NULL;";
    public static final String SQL_GET_EXPIRING_READER_CARDS = "SELECT Books.Title, Readers.Name, Readers.Email" +
            " FROM Books LEFT JOIN Books_Readers ON Books.Id=Books_Readers.Book_Id" +
            " LEFT JOIN Readers ON Books_Readers.Reader_Id=Readers.Id WHERE Due_Date - CURRENT_DATE = ? AND Return_Date IS NULL;";

    @Override
    public List<ReaderCardEntity> getExpiringReaderCards(int days) {
        List<ReaderCardEntity> readerCardEntities = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_EXPIRING_READER_CARDS)) {
                int index = 1;
                preparedStatement.setInt(index++, days);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    readerCardEntities.add(ReaderCardEntity.builder()
                    .bookTitle(resultSet.getString("Title"))
                    .readerName(resultSet.getString("Name"))
                    .readerEmail(resultSet.getString("Email"))
                    .build());
                }
            }
        } catch (SQLException e) {
            logger.debug("Getting expired reader cards error!", e);
        }
        return readerCardEntities;
    }

    @Override
    public int getActiveReaderCardsCount(int bookId) {
        int count = 0;
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(SQL_GET_ACTIVE_READER_CARD_COUNT_BY_BOOK_ID)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.debug("Getting expired reader cards error!", e);
        }
        return count;
    }

    @Override
    public Date getNearestReturnDates(int bookId) {
        Date resultDate = null;
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_NEAREST_RETURN_DATE)) {
                int index = 1;
                preparedStatement.setInt(index++, bookId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultDate = resultSet.getDate("Due_date");
                }
            }
        } catch (SQLException e) {
            logger.debug("Getting nearest available date error!", e);
        }
        return resultDate;
    }

    @Override
    public void add(ReaderCardEntity readerCard, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_READER_CARD);
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

    @Override
    public void update(ReaderCardEntity readerCard, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_READER_CARD);
        int index = 1;
        preparedStatement.setString(index++, readerCard.getStatus());
        preparedStatement.setTimestamp(index++, readerCard.getReturnDate());
        preparedStatement.setString(index++, readerCard.getComment());
        preparedStatement.setInt(index++, readerCard.getId());
        preparedStatement.execute();
    }

    @Override
    public ReaderCardEntity getReaderCard(int readerCardId) {
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERCARD_BY_ID)) {
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
            }
        } catch (SQLException e) {
            logger.debug("Getting reader card error!", e);
        }
        return readerCardEntity;
    }

    @Override
    public List<ReaderCardEntity> get(int bookId) {
        List<ReaderCardEntity> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERCARDS_BY_BOOKID)) {
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
            }
        } catch (SQLException e) {
            logger.debug("Getting reader cards error!", e);
        }
        return resultList;
    }
}
