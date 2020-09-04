package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.BookDto;

import java.util.List;

public interface CoverRepository {
    void add(int bookId, String title);
    void delete(int bookId);
}