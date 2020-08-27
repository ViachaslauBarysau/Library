package by.itechart.repository.impl;


import by.itechart.entity.Book;
import by.itechart.repository.BookRepository;
import by.itechart.service.AuthorService;
import by.itechart.service.impl.AuthorServiceImpl;
import by.itechart.util.ConnectionHelper;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private static BookRepositoryImpl instance = new BookRepositoryImpl();
    public static BookRepositoryImpl getInstance() {
        return instance;
    }
    private AuthorService authorService = AuthorServiceImpl.getInstance();

    private static final int COUNT_OF_BOOKS_ON_PAGE = 10;
    private static final String SQL_DELETE_BOOKS_BY_IDS = "DELETE FROM Books WHERE id = ANY (?);";
    private static final String SQL_GET_COUNT_OF_BOOK_PAGES = "SELECT COUNT(ID) FROM BOOKS;";
    private static final String SQL_GET_PAGE_OF_BOOKS="SELECT Books.*, Authors.Name FROM Books" +
        " LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
        " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
        " WHERE Book_ID IN (SELECT ID FROM BOOKS LIMIT ? OFFSET ?);";


    @Override
    public List<Book> getBooks(int limitOffset) {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PAGE_OF_BOOKS)) {
                preparedStatement.setInt(1, COUNT_OF_BOOKS_ON_PAGE);
                preparedStatement.setInt(2, (limitOffset-1)*COUNT_OF_BOOKS_ON_PAGE);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (bookList.isEmpty() || bookList.get(bookList.size()-1).getId()!=resultSet.getInt("ID")) {
                        bookList.add(Book.builder()
                                .id(resultSet.getInt("ID"))
                                .title(resultSet.getString("Title"))
                                .authors(new ArrayList<>(Arrays.asList(resultSet.getString("Name"))))
                                .publishDate(resultSet.getInt("Publish_date"))
//                                .publisher(resultSet.getString("Publisher"))
//                                .pageCount(resultSet.getInt("Page_count"))
//                                .ISBN(resultSet.getString("Isbn"))
//                                .description(resultSet.getString("Description"))
//                                .totalAmount(resultSet.getInt("Total_amount"))
                                .availableAmount(resultSet.getInt("Available_amount"))
                                .build());
                    } else {
                        bookList.get(bookList.size()-1).getAuthors().add(resultSet.getString("Name"));
                    }

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public int getCountOfPages() {
        int countOfPages = 1;
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COUNT_OF_BOOK_PAGES)) {

                final ResultSet resultSet = preparedStatement.executeQuery();
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


    @Override
    public void deleteBooks(Object[] bookList) {
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_BY_IDS)) {

                Array booksArray = connection.createArrayOf("INTEGER", bookList);
                preparedStatement.setArray(1, booksArray);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
