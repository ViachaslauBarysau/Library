package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
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
            " ON Readers.ID=Books_Readers.ReaderCard_ID WHERE Book_id = ?;";
    private static final String SQL_UPDATE_READER_CARD = "UPDATE Books_Readers SET Book_ID=?," +
            " Reader_ID=?, Borrow_date=?, Time_Period=?, Due_date=?, Return_date=? WHERE ReaderCard_ID=?;";
    private static final String SQL_ADD_READER_CARD = "INSERT INTO Books_Readers(Book_ID, Reader_ID, Borrow_date, Time_Period, " +
            "Due_date) VALUES (?,?,?,?,?);";

    private static final String SQL_GET_READERCARDS_BY_ID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.ReaderCard_ID WHERE ReaderCard_ID = ?;";

    @Override
    public void add(ReaderCardDto readerCardDto) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_READER_CARD)) {
                preparedStatement.setInt(1, readerCardDto.getBookId());
                preparedStatement.setInt(2, readerCardDto.getReaderId());
                preparedStatement.setDate(3, (Date) readerCardDto.getBorrowDate());
                preparedStatement.setInt(4, readerCardDto.getTimePeriod());
                preparedStatement.setDate(5, (Date) readerCardDto.getDueDate());
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ReaderCardDto readerCardDto) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_READER_CARD)) {
                preparedStatement.setInt(1, readerCardDto.getBookId());
                preparedStatement.setInt(2, readerCardDto.getReaderId());
                preparedStatement.setDate(3, (Date) readerCardDto.getBorrowDate());
                preparedStatement.setInt(4, readerCardDto.getTimePeriod());
                preparedStatement.setDate(5, (Date) readerCardDto.getDueDate());
                preparedStatement.setTimestamp(6, readerCardDto.getReturnDate());
                preparedStatement.setInt(7, readerCardDto.getId());
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERCARDS_BY_ID)) {
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
                            .timePeriod(resultSet.getInt("Time_Period"))
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
        List<ReaderCardEntity> list = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERCARDS_BY_BOOKID)) {
                preparedStatement.setInt(1, bookId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list.add(ReaderCardEntity.builder()
                                .id(resultSet.getInt("ReaderCard_ID"))
                                .bookId(resultSet.getInt("book_ID"))
                                .readerId(resultSet.getInt("reader_ID"))
                                .readerName(resultSet.getString("Name"))
                                .readerEmail(resultSet.getString("Email"))
                                .borrowDate(resultSet.getDate("Borrow_date"))
                                .timePeriod(resultSet.getInt("Time_Period"))
                                .dueDate(resultSet.getDate("Due_date"))
                                .returnDate(resultSet.getTimestamp("Return_date"))
                                .comment(resultSet.getString("Comment"))
                                .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
}
