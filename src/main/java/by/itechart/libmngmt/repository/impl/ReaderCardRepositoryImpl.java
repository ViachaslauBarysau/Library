package by.itechart.libmngmt.repository.impl;


import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderCardRepositoryImpl implements ReaderCardRepository {
    private static ReaderCardRepositoryImpl instance = new ReaderCardRepositoryImpl();

    public static ReaderCardRepositoryImpl getInstance() {
        return instance;
    }

    private static final String SQL_GET_READERCARDS_BY_BOOKID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.Reader_ID WHERE Book_id = ?;";
    private static final String SQL_UPDATE_READER_CARD = "UPDATE Books_Readers SET Return_date=?, Comment = ? WHERE ReaderCard_ID = ?;";
    private static final String SQL_ADD_READER_CARD = "INSERT INTO Books_Readers(Book_ID, Reader_ID," +
            " Borrow_date, Status, Due_date, Comment) VALUES (?,?,?,?,?,?);";

    private static final String SQL_GET_READERCARD_BY_ID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.Reader_ID WHERE ReaderCard_ID = ?;";

    private static final String SQL_GET_TWO_NEAREST_RETURN_DATES = "SELECT Due_date FROM Books_Readers WHERE" +
            " Book_id = ? AND Due_date > NOW() AND Return_date IS NULL ORDER BY Due_date ASC LIMIT 2;";

    private static final String SQL_GET_TWO_NEAREST_RETURN_DATE_ID = "SELECT ReaderCard_Id FROM Books_Readers WHERE" +
            " Book_id = ? AND Due_date > NOW() AND Return_date IS NULL ORDER BY Due_date ASC LIMIT 1;";


    @Override
    public int getNearestReturnReaderCardId(int bookId) {
        int id = 0;
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TWO_NEAREST_RETURN_DATE_ID)) {
                preparedStatement.setInt(1, bookId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    id = resultSet.getInt("ReaderCard_ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<Date> getNearestReturnDates(int bookId) {
        List<Date> resultList = new ArrayList();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TWO_NEAREST_RETURN_DATES)) {
                preparedStatement.setInt(1, bookId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(resultSet.getDate("Due_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public void add(ReaderCardEntity readerCard) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_READER_CARD)) {
                int index = 1;
                preparedStatement.setInt(index++, readerCard.getBookId());
                preparedStatement.setInt(index++, readerCard.getReaderId());
                preparedStatement.setDate(index++, readerCard.getBorrowDate());
                preparedStatement.setString(index++, readerCard.getStatus());
                preparedStatement.setDate(index++, readerCard.getDueDate());
                preparedStatement.setString(index++, readerCard.getComment());
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ReaderCardEntity readerCard) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_READER_CARD)) {
                int index = 1;
                preparedStatement.setTimestamp(index++, readerCard.getReturnDate());
                preparedStatement.setString(index++, readerCard.getComment());
                preparedStatement.setInt(index++, readerCard.getId());
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReaderCardEntity getReaderCard(int readerCardId) {
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERCARD_BY_ID)) {
                preparedStatement.setInt(1, readerCardId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    readerCardEntity = ReaderCardEntity.builder()
                            .id(resultSet.getInt("ReaderCard_ID"))
                            .bookId(resultSet.getInt("book_ID"))
                            .readerId(resultSet.getInt("reader_ID"))
                            .readerName(resultSet.getString("Name"))
                            .readerEmail(resultSet.getString("Email"))
                            .borrowDate(resultSet.getDate("Borrow_date"))
                            .status(resultSet.getString("Status"))
                            .dueDate(resultSet.getDate("Due_date"))
                            .returnDate(resultSet.getTimestamp("Return_date"))
                            .comment(resultSet.getString("Comment"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readerCardEntity;
    }

    @Override
    public List<ReaderCardEntity> get(int bookId) {
        List<ReaderCardEntity> resultList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERCARDS_BY_BOOKID)) {
                preparedStatement.setInt(1, bookId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultList.add(ReaderCardEntity.builder()
                                .id(resultSet.getInt("ReaderCard_ID"))
                                .bookId(resultSet.getInt("book_ID"))
                                .readerId(resultSet.getInt("reader_ID"))
                                .readerName(resultSet.getString("Name"))
                                .readerEmail(resultSet.getString("Email"))
                                .borrowDate(resultSet.getDate("Borrow_date"))
                                .status(resultSet.getString("Status"))
                                .dueDate(resultSet.getDate("Due_date"))
                                .returnDate(resultSet.getTimestamp("Return_date"))
                                .comment(resultSet.getString("Comment"))
                                .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;

    }
}
