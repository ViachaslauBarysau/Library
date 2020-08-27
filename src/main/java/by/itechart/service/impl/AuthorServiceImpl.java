package by.itechart.service.impl;

import by.itechart.repository.AuthorRepository;
import by.itechart.repository.BookRepository;
import by.itechart.repository.impl.AuthorRepositoryImpl;
import by.itechart.repository.impl.BookRepositoryImpl;
import by.itechart.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository = AuthorRepositoryImpl.getInstance();
    private static AuthorServiceImpl instance = new AuthorServiceImpl();

    public static AuthorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<String> getListAuthorsByBookId(int bookId) {
        return authorRepository.getListAuthorsByBookId(bookId);
    }
}
