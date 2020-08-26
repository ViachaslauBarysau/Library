package by.itechart.repository.impl;


import by.itechart.entity.Book;
import by.itechart.repository.BookRepository;
import by.itechart.util.ConnectionHelper;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private static BookRepositoryImpl instance = new BookRepositoryImpl();
    public static BookRepositoryImpl getInstance() {
        return instance;
    }

    private static final int COUNT_OF_BOOKS_ON_PAGE = 10;
    private static final String SQL_GET_COUNT_OF_BOOK_PAGES = "SELECT COUNT(ID) FROM BOOKS;";
    private static final String SQL_GET_PAGE_OF_BOOKS="SELECT * FROM BOOKS LIMIT ? OFFSET ?;";


    @Override
    public List<Book> getPageOfBooks(int pageNumber) {
        List<Book> pageOfBooks = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PAGE_OF_BOOKS)) {
                preparedStatement.setInt(1, COUNT_OF_BOOKS_ON_PAGE);
                preparedStatement.setInt(2, (pageNumber-1)*COUNT_OF_BOOKS_ON_PAGE);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    pageOfBooks.add(Book.builder()
                            .id(resultSet.getInt("ID"))
                            .title(resultSet.getString("Title"))
                            .publishDate(resultSet.getInt("Publish_date"))
                            .publisher(resultSet.getString("Publisher"))
                            .pageCount(resultSet.getInt("Page_count"))
                            .ISBN(resultSet.getString("Isbn"))
                            .description(resultSet.getString("Description"))
                            .totalAmount(resultSet.getInt("Total_amount"))
                            .availableAmount(resultSet.getInt("Available_amount"))
                            .build());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageOfBooks;
    }

    @Override
    public int getCountOfPages() {
        int countOfPages = 1;
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                final ResultSet resultSet = statement.executeQuery(SQL_GET_COUNT_OF_BOOK_PAGES);
                while(resultSet.next()){
                    int booksCount = resultSet.getInt(1);
                    if (booksCount%COUNT_OF_BOOKS_ON_PAGE==0) {
                        countOfPages = booksCount/(COUNT_OF_BOOKS_ON_PAGE);
                    } else {
                        countOfPages = booksCount/(COUNT_OF_BOOKS_ON_PAGE) + 1;
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countOfPages;
    }
}
