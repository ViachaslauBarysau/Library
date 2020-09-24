package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.repository.impl.GenreRepositoryImpl;
import by.itechart.libmngmt.service.GenreService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository = GenreRepositoryImpl.getInstance();
    private static GenreServiceImpl instance;

    public static synchronized GenreServiceImpl getInstance() {
        if(instance == null){
            instance = new GenreServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(BookDto bookDto) {
        List<String> allGenresList = genreRepository.findAll();
        List<String> bookGenresList =  new ArrayList<>();
        bookGenresList.addAll(bookDto.getGenres());
        bookGenresList.removeAll(allGenresList);
        for (String genre: bookGenresList) {
            genreRepository.add(genre);
        }

        genreRepository.deleteBooksGenres(bookDto.getId());

        List<Integer> genreIDs = genreRepository.getGenreIds(bookDto.getGenres());
        for (int genreId: genreIDs) {
            genreRepository.addBookGenre(bookDto.getId(), genreId);
        }
    }

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {

        List<String> allGenresList = genreRepository.findAll(connection);
        List<String> bookGenresList = new ArrayList<>();
        bookGenresList.addAll(bookDto.getGenres());
        bookGenresList.removeAll(allGenresList);
        for (String genre: bookGenresList) {
            genreRepository.add(genre, connection);
        }

        genreRepository.deleteBooksGenres(bookDto.getId(), connection);

        List<Integer> genreIDs = genreRepository.getGenreIds(bookDto.getGenres(), connection);
        for (int genreId: genreIDs) {
            genreRepository.addBookGenre(bookDto.getId(), genreId, connection);
        }
    }
}
