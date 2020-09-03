package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.repository.impl.GenreRepositoryImpl;
import by.itechart.libmngmt.service.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {
    private GenreRepository genreRepository = GenreRepositoryImpl.getInstance();
    private static GenreServiceImpl instance = new GenreServiceImpl();

    public static GenreServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void add(BookDto bookDto) {
        List<String> allGenresList = genreRepository.get();
        List<String> bookGenresList = bookDto.getGenres();
        bookGenresList.removeAll(allGenresList);
        for (int index=0; index<bookGenresList.size(); index++) {
            genreRepository.add(bookGenresList.get(index));
        }
        Object[] genreTitles = bookGenresList.toArray();
        List<Integer> genreIDs = genreRepository.getId(genreTitles);

        genreRepository.deleteBooksGenresRecords(bookDto.getId());

        for (int genreId: genreIDs
        ) {
            genreRepository.addBookGenreRecord(bookDto.getId(), genreId);
        }
    }
}
