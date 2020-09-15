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

    public static GenreServiceImpl getInstance() {
        if(instance == null){
            instance = new GenreServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(BookDto bookDto) {
        List<String> allGenresList = genreRepository.get();
        List<String> bookGenresList = new ArrayList<>();
        bookGenresList =  bookDto.getGenres();
        bookGenresList.removeAll(allGenresList);
        for (String genre: bookGenresList
        ) {
            genreRepository.add(genre);
        }
        Object[] genreTitles = bookDto.getGenres().toArray();
        List<Integer> genreIDs = genreRepository.getId(genreTitles);

        genreRepository.deleteBooksGenresRecords(bookDto.getId());

        for (int genreId: genreIDs
        ) {
            genreRepository.addBookGenreRecord(bookDto.getId(), genreId);
        }
    }

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {

        List<String> allGenresList = genreRepository.get(connection);
        List<String> bookGenresList = new ArrayList<>();
        bookGenresList.addAll(bookDto.getGenres());
        bookGenresList.removeAll(allGenresList);

        for (String genre: bookGenresList
             ) {
            genreRepository.add(genre, connection);
        }

        Object[] genreTitles = bookDto.getGenres().toArray();
        List<Integer> genreIDs = genreRepository.getId(genreTitles, connection);

        genreRepository.deleteBooksGenresRecords(bookDto.getId(), connection);

        for (int genreId: genreIDs
        ) {
            genreRepository.addBookGenreRecord(bookDto.getId(), genreId, connection);
        }
    }
}
