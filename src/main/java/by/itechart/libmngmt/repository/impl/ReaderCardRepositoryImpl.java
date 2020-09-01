package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.entity.Book;
import by.itechart.libmngmt.entity.ReaderCard;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReaderCardRepositoryImpl implements ReaderCardRepository {
    private static ReaderCardRepositoryImpl instance = new ReaderCardRepositoryImpl();

    public static ReaderCardRepositoryImpl getInstance() {
        return instance;
    }

    private static final String SQL_GET_READERCARDS_BY_BOOKID = "SELECT * FROM Books_Readers LEFT JOIN Readers" +
            " ON Readers.ID=Books_Readers.ReaderCard_ID WHERE Book_id = ?;";


    @Override
    public List<ReaderCard> get(int bookId) {
        List<ReaderCard> list = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_READERCARDS_BY_BOOKID)) {
                preparedStatement.setInt(1, bookId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list.add(ReaderCard.builder()
                                .id(resultSet.getInt("ReaderCard_ID"))
                                .bookId(resultSet.getInt("book_ID"))
                                .readerId(resultSet.getInt("reader_ID"))
                                .readerName(resultSet.getString("Name"))
                                .readerEmail(resultSet.getString("Email"))
                                .borrowDate(resultSet.getDate("Borrow_date"))
                                .timePeriod(resultSet.getInt("Time_Period"))
                                .dueDate(resultSet.getDate("Due_date"))
                                .returnDate(resultSet.getTimestamp("Return_date"))
                                .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
}
