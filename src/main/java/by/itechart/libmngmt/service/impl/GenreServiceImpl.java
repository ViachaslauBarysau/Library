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

/**
 * Provides methods for operations with genres.
 */
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

    /**
     * Adds genre's titles of specific book to the database. First, method
     * finds not existing genres and adds them to the database, then deletes
     * records from Books_Genres table and adds new records there.
     *
     * @param bookDto    BookDto object
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void add(final BookDto bookDto, final Connection connection) throws SQLException {
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
