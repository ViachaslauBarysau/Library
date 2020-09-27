package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.repository.impl.GenreRepositoryImpl;
import by.itechart.libmngmt.service.GenreService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreServiceImpl implements GenreService {
    private GenreRepository genreRepository = GenreRepositoryImpl.getInstance();
    private static volatile GenreServiceImpl instance;

    public static synchronized GenreServiceImpl getInstance() {
        GenreServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (GenreServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GenreServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {
        addNotExistGenres(bookDto, connection);
        genreRepository.deleteBooksGenresRecords(bookDto.getId(), connection);
        addBooksGenresRecords(bookDto, connection);
    }

    private void addNotExistGenres(BookDto bookDto, Connection connection) throws SQLException {
        List<String> allGenresList = genreRepository.findAll(connection);
        List<String> bookGenresList = new ArrayList<>();
        bookGenresList.addAll(bookDto.getGenres());
        bookGenresList.removeAll(allGenresList);
        for (String genre : bookGenresList) {
            genreRepository.add(genre, connection);
        }
    }

    private void addBooksGenresRecords(BookDto bookDto, Connection connection) throws SQLException {
        List<Integer> genreIDs = genreRepository.getGenreIds(bookDto.getGenres(), connection);
        for (int genreId : genreIDs) {
            genreRepository.addBookGenreRecord(bookDto.getId(), genreId, connection);
        }
    }
}
