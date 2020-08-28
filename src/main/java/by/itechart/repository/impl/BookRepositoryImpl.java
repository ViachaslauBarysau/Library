package by.itechart.repository.impl;


import by.itechart.entity.Book;
import by.itechart.repository.BookRepository;
import by.itechart.service.AuthorService;
import by.itechart.service.impl.AuthorServiceImpl;
import by.itechart.util.ConnectionHelper;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
    private static final String SQL_GET_BOOK="SELECT Books.*, Authors.Name, Genres.Title AS Genre," +
            " Covers.Title AS Cover FROM Books" +
            " LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
            " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
            " LEFT JOIN Books_Genres ON Books.Id=Books_Genres.Book_Id" +
            " LEFT JOIN Genres ON Books_Genres.Genre_Id=Genres.Id" +
            " LEFT JOIN Covers ON Covers.Book_Id=Books.Id" +
            " WHERE Books.ID = ?;";


    @Override
    public List<Book> getBookEntities(int limitOffset) {
        List<Book> bookEntitiesList = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PAGE_OF_BOOKS)) {
                preparedStatement.setInt(1, COUNT_OF_BOOKS_ON_PAGE);
                preparedStatement.setInt(2, (limitOffset-1)*COUNT_OF_BOOKS_ON_PAGE);
                final ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (bookEntitiesList.isEmpty() || bookEntitiesList.get(bookEntitiesList.size()-1).getId()!=resultSet.getInt("ID")) {
                        bookEntitiesList.add(Book.builder()
                                .id(resultSet.getInt("ID"))
                                .title(resultSet.getString("Title"))
                                .authors(new ArrayList<>(Arrays.asList(resultSet.getString("Name"))))
                                .publishDate(resultSet.getInt("Publish_date"))
                                .availableAmount(resultSet.getInt("Available_amount"))
                                .build());
                    } else {
                        bookEntitiesList.get(bookEntitiesList.size()-1).getAuthors().add(resultSet.getString("Name"));
                    }

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookEntitiesList;
    }

    @Override
    public Book getBook(int bookId) {
        Book book = new Book();
        try (Connection connection = ConnectionHelper.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BOOK)) {
                preparedStatement.setInt(1, bookId);

                final ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        book = Book.builder()
                                .id(resultSet.getInt("ID"))
                                .title(resultSet.getString("Title"))
                                .publishDate(resultSet.getInt("Publish_date"))
                                .publisher(resultSet.getString("Publisher"))
                                .pageCount(resultSet.getInt("Page_count"))
                                .ISBN(resultSet.getString("Isbn"))
                                .description(resultSet.getString("Description"))
                                .totalAmount(resultSet.getInt("Total_amount"))
                                .availableAmount(resultSet.getInt("Available_amount"))
                                .description("Description")
                                .authors(new ArrayList<>())
                                .covers(new ArrayList<>())
                                .genres(new ArrayList<>())
                                .build();
                        break;
                    }
                HashSet<String> authors = new HashSet<>();
                HashSet<String> genres = new HashSet<>();
                HashSet<String> covers = new HashSet<>();
                while (resultSet.next()) {
                    if (resultSet.getString("Name") != null) {
                        authors.add(resultSet.getString("Name"));
                    }
                    if (resultSet.getString("Genre")!= null) {
                        genres.add(resultSet.getString("Genre"));
                    }
                    if (resultSet.getString("Cover") != null) {
                        covers.add(resultSet.getString("Cover"));
                    }
                }
                book.getAuthors().addAll(authors);
                book.getGenres().addAll(genres);
                book.getCovers().addAll(covers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
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
