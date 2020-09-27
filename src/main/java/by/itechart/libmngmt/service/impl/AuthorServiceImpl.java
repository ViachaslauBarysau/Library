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

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {
        addNotExistAuthors(bookDto, connection);
        authorRepository.deleteBooksAuthorsRecords(bookDto.getId(), connection);
        addBooksAuthorsRecords(bookDto, connection);
    }

    private void addNotExistAuthors(BookDto bookDto, Connection connection) throws SQLException {
        List<String> allAuthorsList = authorRepository.findAll(connection);
        List<String> bookAuthorsList = new ArrayList<>();
        bookAuthorsList.addAll(bookDto.getAuthors());
        bookAuthorsList.removeAll(allAuthorsList);
        for (String author : bookAuthorsList) {
            authorRepository.add(author, connection);
        }
    }

    private void addBooksAuthorsRecords(BookDto bookDto, Connection connection) throws SQLException {
        List<Integer> authorIDs = authorRepository.getAuthorIds(bookDto.getAuthors(), connection);
        for (int authorID : authorIDs) {
            authorRepository.addBookAuthorRecord(bookDto.getId(), authorID, connection);
        }
    }
}
