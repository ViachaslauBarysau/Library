package by.itechart.libmngmt.repository.impl;

import by.itechart.libmngmt.dto.BookAddDto;
import by.itechart.libmngmt.repository.CoverRepository;

import java.util.List;

public class CoverRepositoryImpl implements CoverRepository {
    private static CoverRepositoryImpl instance = new CoverRepositoryImpl();
    public static CoverRepositoryImpl getInstance() {
        return instance;
    }

    private static final String SQL_GET_BOOK_COVERS = "SELECT Title FROM Covers;";

    @Override
    public void add(BookAddDto bookAddDto) {

    }

    @Override
    public List<String> get() {
        return null;
    }

}
