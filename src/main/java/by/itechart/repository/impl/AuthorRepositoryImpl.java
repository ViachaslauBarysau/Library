package by.itechart.repository.impl;

import by.itechart.entity.Book;
import by.itechart.repository.AuthorRepository;
import by.itechart.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {

    private static AuthorRepositoryImpl instance = new AuthorRepositoryImpl();
    public static AuthorRepositoryImpl getInstance() {
        return instance;
    }

    private static final String SQL_SELECT_AUTHORS_BY_BOOKID = "SELECT Authors.Name FROM Authors LEFT JOIN Books_Authors ON Authors.ID=Books_Authors.Author_ID WHERE Books_Authors.Book_Id=?;";

    @Override
    public List<String> getListAuthorsByBookId(int bookId) {
        List<String> authorsOfBook = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_AUTHORS_BY_BOOKID)) {
                preparedStatement.setInt(1, bookId);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    authorsOfBook.add(resultSet.getString(1));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorsOfBook;
    }
}
