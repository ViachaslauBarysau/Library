package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookAddDto;
import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.repository.impl.AuthorRepositoryImpl;
import by.itechart.libmngmt.repository.impl.CoverRepositoryImpl;
import by.itechart.libmngmt.service.AuthorService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository = AuthorRepositoryImpl.getInstance();

    private static AuthorServiceImpl instance = new AuthorServiceImpl();

    public static AuthorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void add(BookAddDto bookAddDto) {
        List<String> allAuthorsList = authorRepository.get();
        List<String> bookAuthorsList = bookAddDto.getAuthors();
        bookAuthorsList.removeAll(allAuthorsList);
        for (int index=0; index<bookAuthorsList.size(); index++) {
            authorRepository.add(bookAuthorsList.get(index));
        }
            Object[] authorNames = bookAuthorsList.toArray();
        List<Integer> authorIDs = new ArrayList<>();
        authorIDs = authorRepository.getId(authorNames);
    }

}
