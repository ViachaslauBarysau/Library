package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.repository.impl.AuthorRepositoryImpl;
import by.itechart.libmngmt.service.AuthorService;


import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository = AuthorRepositoryImpl.getInstance();

    private static AuthorServiceImpl instance = new AuthorServiceImpl();

    public static AuthorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void add(BookDto bookDto) {
        List<String> allAuthorsList = authorRepository.get();
        List<String> bookAuthorsList = new ArrayList<>();
        bookAuthorsList.addAll(bookDto.getAuthors());
        bookAuthorsList.removeAll(allAuthorsList);

        for (String author: bookAuthorsList
        ) {
            authorRepository.add(author);
        }

        Object[] authorNames = bookDto.getAuthors().toArray();
        List<Integer> authorIDs = authorRepository.getId(authorNames);

        authorRepository.deleteBooksAuthorsRecords(bookDto.getId());

        for (int authorID: authorIDs
             ) {
            authorRepository.addBookAuthorRecord(bookDto.getId(), authorID);
        }
    }

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {
        List<String> allAuthorsList = authorRepository.get(connection);
        List<String> bookAuthorsList = new ArrayList<>();
        bookAuthorsList.addAll(bookDto.getAuthors());
        bookAuthorsList.removeAll(allAuthorsList);
        for (String author: bookAuthorsList
        ) {
            authorRepository.add(author, connection);
        }
        Object[] authorNames = bookDto.getAuthors().toArray();
        List<Integer> authorIDs = authorRepository.getId(authorNames, connection);

        authorRepository.deleteBooksAuthorsRecords(bookDto.getId(), connection);

        for (int authorID: authorIDs
        ) {
            authorRepository.addBookAuthorRecord(bookDto.getId(), authorID, connection);
        }
    }

}
