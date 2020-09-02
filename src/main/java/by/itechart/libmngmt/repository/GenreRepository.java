package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.BookAddDto;

import java.util.List;

public interface GenreRepository {
    void add(BookAddDto bookAddDto);
    List<String> get();
}
