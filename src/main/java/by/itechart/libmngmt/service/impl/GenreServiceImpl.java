package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookAddDto;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.repository.impl.CoverRepositoryImpl;
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
    public void add(BookAddDto bookAddDto) {

    }
}
