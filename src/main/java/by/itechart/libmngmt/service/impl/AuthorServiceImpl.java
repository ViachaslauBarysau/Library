package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.repository.impl.AuthorRepositoryImpl;
import by.itechart.libmngmt.service.AuthorService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for operations with authors.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository = AuthorRepositoryImpl.getInstance();
    private static volatile AuthorServiceImpl instance;

    public static synchronized AuthorServiceImpl getInstance() {
        AuthorServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (AuthorServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AuthorServiceImpl();
                }
            }
        }
        return localInstance;
    }

    /**
     * Adds author's names of specific book to the database. First, method
     * finds not existing names and adds them to the database, then deletes
     * old records from Books_Authors table and adds new records there.
     *
     * @param bookDto    BookDto object
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void add(final BookDto bookDto, final Connection connection) throws SQLException {
        addNotExistAuthors(bookDto, connection);
        authorRepository.deleteBooksAuthorsRecords(bookDto.getId(), connection);
        addBooksAuthorsRecords(bookDto, connection);
    }

    private void addNotExistAuthors(final BookDto bookDto, final Connection connection) throws SQLException {
        List<String> allAuthorsList = authorRepository.findAll(connection);
        List<String> bookAuthorsList = new ArrayList<>();
        bookAuthorsList.addAll(bookDto.getAuthors());
        bookAuthorsList.removeAll(allAuthorsList);
        for (String author : bookAuthorsList) {
            authorRepository.add(author, connection);
        }
    }

    private void addBooksAuthorsRecords(final BookDto bookDto, final Connection connection) throws SQLException {
        List<Integer> authorIds = authorRepository.getAuthorIds(bookDto.getAuthors(), connection);
        for (int authorId : authorIds) {
            authorRepository.addBookAuthorRecord(bookDto.getId(), authorId, connection);
        }
    }
}
