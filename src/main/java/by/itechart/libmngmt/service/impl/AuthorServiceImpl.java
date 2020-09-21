package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.repository.impl.AuthorRepositoryImpl;
import by.itechart.libmngmt.service.AuthorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository = AuthorRepositoryImpl.getInstance();
    private static AuthorServiceImpl instance;

    public static AuthorServiceImpl getInstance() {
        if(instance == null){
            instance = new AuthorServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(BookDto bookDto) {
        List<String> allAuthorsList = authorRepository.findAll();
        List<String> bookAuthorsList = new ArrayList<>();
        bookAuthorsList.addAll(bookDto.getAuthors());
        bookAuthorsList.removeAll(allAuthorsList);
        for (String author: bookAuthorsList
        ) {
            authorRepository.add(author);
        }

        authorRepository.deleteBooksAuthorsRecords(bookDto.getId());

        List<Integer> authorIDs = authorRepository.getId(bookDto.getAuthors());
        for (int authorID: authorIDs) {
            authorRepository.addBookAuthorRecord(bookDto.getId(), authorID);
        }
    }

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {
        List<String> allAuthorsList = authorRepository.findAll(connection);
        List<String> bookAuthorsList = new ArrayList<>();
        bookAuthorsList.addAll(bookDto.getAuthors());
        bookAuthorsList.removeAll(allAuthorsList);
        for (String author: bookAuthorsList) {
            authorRepository.add(author, connection);
        }

        authorRepository.deleteBooksAuthorsRecords(bookDto.getId(), connection);

        List<Integer> authorIDs = authorRepository.getId(bookDto.getAuthors(), connection);
        for (int authorID: authorIDs) {
            authorRepository.addBookAuthorRecord(bookDto.getId(), authorID, connection);
        }
    }

}
