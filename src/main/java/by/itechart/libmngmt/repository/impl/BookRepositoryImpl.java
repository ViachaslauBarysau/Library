package by.itechart.libmngmt.repository.impl;


import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.util.ConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Provides methods for CRUD operations with books.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookRepositoryImpl implements BookRepository {
    private static final Logger LOGGER = LogManager.getLogger(BookRepositoryImpl.class.getName());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static volatile BookRepositoryImpl instance;

    public static synchronized BookRepositoryImpl getInstance() {
        BookRepositoryImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (BookRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BookRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    private static final int BOOK_PAGE_COUNT = 10;
    private static final String SQL_DELETE_BOOKS_BY_IDS = "DELETE FROM Books WHERE id = ANY (?);";
    private static final String SQL_GET_PAGE_COUNT = "SELECT COUNT(ID) FROM Books;";
    private static final String SQL_GET_AVAILABLE_PAGE_COUNT = "SELECT COUNT(ID) FROM Books WHERE Available_amount>0;";
    private static final String SQL_GET_BOOK_PAGE = "SELECT Books.*, Authors.Name FROM Books" +
            " LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
            " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
            " WHERE Book_ID IN (SELECT ID FROM BOOKS ORDER BY Title ASC LIMIT ? OFFSET ?) ORDER BY Title ASC;";
    private static final String SQL_GET_AVAILABLE_BOOK_PAGE = "SELECT Books.*, Authors.Name FROM Books" +
            " LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
            " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
            " WHERE Book_ID IN (SELECT ID FROM BOOKS WHERE Available_amount >0 ORDER BY Title ASC LIMIT ? OFFSET ?)" +
            " ORDER BY Title ASC;";
    private static final String SQL_SEARCH_BOOKS = "SELECT Books.*, Authors.Name FROM Books" +
            " LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
            " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
            " WHERE Books.Title IN (SELECT DISTINCT Books.Title FROM Books" +
            " LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
            " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
            " LEFT JOIN Books_Genres ON Books_Genres.Book_Id = Books.Id" +
            " LEFT JOIN Genres ON Genres.Id=Books_Genres.Genre_Id" +
            " WHERE lower(Books.title) LIKE ? AND lower(name) LIKE ? AND lower(Genres.Title) LIKE ? AND" +
            " lower(description) LIKE ? ORDER BY Books.Title ASC LIMIT ? OFFSET ?) ORDER BY Title ASC;";
    private static final String SQL_GET_SEARCH_PAGE_COUNT = "SELECT COUNT(ID) FROM (SELECT DISTINCT Books.ID" +
            " FROM Books LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
            " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
            " LEFT JOIN Books_Genres ON Books_Genres.Book_Id = Books.Id" +
            " LEFT JOIN Genres on Genres.Id=Books_Genres.Genre_Id" +
            " WHERE lower(Books.title) LIKE ? AND lower(name) LIKE ? AND" +
            " lower(Genres.Title) LIKE ? AND lower(description) LIKE ?);";
    private static final String SQL_GET_BOOK = "SELECT Books.*, Authors.Name, Genres.Title AS Genre," +
            " Covers.Title AS Cover FROM Books" +
            " LEFT JOIN Books_Authors ON Books_Authors.Book_Id=Books.Id" +
            " LEFT JOIN Authors ON Authors.Id=Books_Authors.Author_Id" +
            " LEFT JOIN Books_Genres ON Books.Id=Books_Genres.Book_Id" +
            " LEFT JOIN Genres ON Books_Genres.Genre_Id=Genres.Id" +
            " LEFT JOIN Covers ON Covers.Book_Id=Books.Id" +
            " WHERE Books.ID = ?;";
    private static final String SQL_ADD_BOOK = "INSERT INTO Books(Title, Publisher, Publish_date, Page_count, " +
            "Isbn, Description, Total_amount, Available_amount) VALUES (?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE_BOOK = "UPDATE Books SET Title=?, Publisher=?, Publish_date=?," +
            " Page_count=?, ISBN=?, Description=?, Total_amount=?, Available_amount=? WHERE Id=?;";

    /**
     * Returns count of pages with specific search results.
     *
     * @param searchParams list of search parameters
     * @return count of pages
     */
    @Override
    public int getSearchPageCount(final List<String> searchParams) {
        int searchPageCount = 1;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_SEARCH_PAGE_COUNT)) {
            int index = 1;
            int paramsIndex = 0;
            preparedStatement.setString(index++, searchParams.get(paramsIndex++));
            preparedStatement.setString(index++, searchParams.get(paramsIndex++));
            preparedStatement.setString(index++, searchParams.get(paramsIndex++));
            preparedStatement.setString(index++, searchParams.get(paramsIndex++));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int booksCount = resultSet.getInt(1);
                searchPageCount = (booksCount % BOOK_PAGE_COUNT == 0) ? booksCount / (BOOK_PAGE_COUNT) :
                        booksCount / (BOOK_PAGE_COUNT) + 1;
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting search book results error.", e);
        }
        return searchPageCount;
    }

    /**
     * Updates fields of existing book.
     *
     * @param book       BookEntity for updating
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void update(final BookEntity book, final Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            int index = 1;
            preparedStatement.setString(index++, book.getTitle());
            preparedStatement.setString(index++, book.getPublisher());
            preparedStatement.setInt(index++, book.getPublishDate());
            preparedStatement.setInt(index++, book.getPageCount());
            preparedStatement.setString(index++, book.getIsbn());
            preparedStatement.setString(index++, book.getDescription());
            preparedStatement.setInt(index++, book.getTotalAmount());
            preparedStatement.setInt(index++, book.getAvailableAmount());
            preparedStatement.setInt(index++, book.getId());
            preparedStatement.execute();
        }
    }

    /**
     * Returns count of pages without unavailable books.
     *
     * @return count of pages
     */
    @Override
    public int getAvailableBookPageCount() {
        int pageCount = 1;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AVAILABLE_PAGE_COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int booksCount = resultSet.getInt(1);
                pageCount = (booksCount % BOOK_PAGE_COUNT == 0) ? booksCount / (BOOK_PAGE_COUNT) :
                        booksCount / (BOOK_PAGE_COUNT) + 1;
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting available books page count error.", e);
        }
        return pageCount;
    }

    /**
     * Returns list of available books(one specific page).
     *
     * @param offset page number
     * @return list of BookEntities
     */
    @Override
    public List<BookEntity> findAvailable(final int offset) {
        List<BookEntity> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_AVAILABLE_BOOK_PAGE)) {
            int index = 1;
            preparedStatement.setInt(index++, BOOK_PAGE_COUNT);
            preparedStatement.setInt(index++, (offset - 1) * BOOK_PAGE_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultList.isEmpty() ||
                        resultList.get(resultList.size() - 1).getId() != resultSet.getInt("ID")) {
                    resultList.add(BookEntity.builder()
                            .id(resultSet.getInt("ID"))
                            .title(resultSet.getString("Title"))
                            .authors(new ArrayList<>(Arrays.asList(resultSet.getString("Name"))))
                            .publishDate(resultSet.getInt("Publish_date"))
                            .availableAmount(resultSet.getInt("Available_amount"))
                            .build());
                } else {
                    resultList.get(resultList.size() - 1).getAuthors().add(resultSet.getString("Name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting available books error.", e);
        }
        return resultList;
    }

    /**
     * Adds book to the database and returns ID of created book.
     *
     * @param book       BookEntity for adding
     * @param connection current connection
     * @return ID of created book
     * @throws SQLException in case of SQL failure
     */
    @Override
    public int add(final BookEntity book, final Connection connection) throws SQLException {
        int id = 0;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_ADD_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            preparedStatement.setString(index++, book.getTitle());
            preparedStatement.setString(index++, book.getPublisher());
            preparedStatement.setInt(index++, book.getPublishDate());
            preparedStatement.setInt(index++, book.getPageCount());
            preparedStatement.setString(index++, book.getIsbn());
            preparedStatement.setString(index++, book.getDescription());
            preparedStatement.setInt(index++, book.getTotalAmount());
            preparedStatement.setInt(index++, book.getAvailableAmount());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }
        return id;
    }

    /**
     * Returns specific page of books.
     *
     * @param offset page number
     * @return list of BookEntities
     */
    @Override
    public List<BookEntity> findAll(final int offset) {
        List<BookEntity> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BOOK_PAGE)) {
            int index = 1;
            preparedStatement.setInt(index++, BOOK_PAGE_COUNT);
            preparedStatement.setInt(index++, (offset - 1) * BOOK_PAGE_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultList.isEmpty() || resultList.get(resultList.size() - 1)
                        .getId() != resultSet.getInt("ID")) {
                    resultList.add(BookEntity.builder()
                            .id(resultSet.getInt("ID"))
                            .title(resultSet.getString("Title"))
                            .authors(new ArrayList<>(Arrays.asList(resultSet.getString("Name"))))
                            .publishDate(resultSet.getInt("Publish_date"))
                            .availableAmount(resultSet.getInt("Available_amount"))
                            .build());
                } else {
                    resultList.get(resultList.size() - 1).getAuthors().add(resultSet.getString("Name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting books error.", e);
        }
        return resultList;
    }

    /**
     * Returns book by book ID.
     *
     * @param bookId ID of book
     * @return BookEntity object
     */
    @Override
    public BookEntity find(final int bookId) {
        BookEntity book = null;
        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement =
                connection.prepareStatement(SQL_GET_BOOK, ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)) {
            int index = 1;
            preparedStatement.setInt(index++, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                book = BookEntity.builder()
                        .id(resultSet.getInt("ID"))
                        .title(resultSet.getString("Title"))
                        .publishDate(resultSet.getInt("Publish_date"))
                        .publisher(resultSet.getString("Publisher"))
                        .pageCount(resultSet.getInt("Page_count"))
                        .isbn(resultSet.getString("Isbn"))
                        .description(resultSet.getString("Description"))
                        .totalAmount(resultSet.getInt("Total_amount"))
                        .availableAmount(resultSet.getInt("Available_amount"))
                        .authors(new ArrayList<>())
                        .covers(new ArrayList<>())
                        .genres(new ArrayList<>())
                        .build();
                break;
            }
            Set<String> authors = new HashSet<>();
            Set<String> genres = new HashSet<>();
            Set<String> covers = new HashSet<>();
            if (book != null) {
                fillGenresAuthorsCovers(authors, genres, covers, resultSet);
            }
            book.getAuthors().addAll(authors);
            book.getGenres().addAll(genres);
            book.getCovers().addAll(covers);
        } catch (SQLException e) {
            LOGGER.debug("Getting book error.", e);
        }
        return book;
    }

    /**
     * Returns count of pages of all books.
     *
     * @return count of pages
     */
    @Override
    public int getAllPageCount() {
        int pageCount = 1;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PAGE_COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int booksCount = resultSet.getInt(1);
                pageCount = (booksCount % BOOK_PAGE_COUNT == 0) ? booksCount / (BOOK_PAGE_COUNT) :
                        booksCount / (BOOK_PAGE_COUNT) + 1;
            }
        } catch (SQLException e) {
            LOGGER.debug("Getting book's page count error.", e);
        }
        return pageCount;
    }

    /**
     * Deletes books by IDs.
     *
     * @param bookList list of book's IDs for deleting
     */
    @Override
    public void delete(final List<Integer> bookList) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOKS_BY_IDS)) {
            Array booksArray = connection.createArrayOf("INTEGER", bookList.toArray());
            int index = 1;
            preparedStatement.setArray(index++, booksArray);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.debug("Deleting books error.", e);
        }
    }

    /**
     * Returns list of books searched by search parameters(one specific page).
     *
     * @param searchParams list of book's IDs for deleting
     * @param offset       page number
     * @return list of BookEntity
     */
    @Override
    public List<BookEntity> search(final List<String> searchParams, final int offset) {
        List<BookEntity> resultList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SEARCH_BOOKS)) {
            int index = 1;
            preparedStatement.setString(index++, searchParams.get(0));
            preparedStatement.setString(index++, searchParams.get(1));
            preparedStatement.setString(index++, searchParams.get(2));
            preparedStatement.setString(index++, searchParams.get(3));
            preparedStatement.setInt(index++, BOOK_PAGE_COUNT);
            preparedStatement.setInt(index++, (offset - 1) * BOOK_PAGE_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultList.isEmpty() || (resultList.get(resultList.size() - 1).getId()
                        != resultSet.getInt("ID"))) {
                    resultList.add(BookEntity.builder()
                            .id(resultSet.getInt("ID"))
                            .title(resultSet.getString("Title"))
                            .authors(new ArrayList<>(Arrays.asList(resultSet.getString("Name"))))
                            .publishDate(resultSet.getInt("Publish_date"))
                            .availableAmount(resultSet.getInt("Available_amount"))
                            .build());
                } else {
                    resultList.get(resultList.size() - 1).getAuthors().add(resultSet.getString("Name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("Searching books error.", e);
        }
        return resultList;
    }

    private void fillGenresAuthorsCovers(final Set authors, final Set genres, final Set covers,
                                         final ResultSet resultSet) throws SQLException {
        resultSet.beforeFirst();
        while (resultSet.next()) {
            if (resultSet.getString("Name") != null) {
                authors.add(resultSet.getString("Name"));
            }
            if (resultSet.getString("Genre") != null) {
                genres.add(resultSet.getString("Genre"));
            }
            if (resultSet.getString("Cover") != null) {
                covers.add(resultSet.getString("Cover"));
            }
        }
    }
}
