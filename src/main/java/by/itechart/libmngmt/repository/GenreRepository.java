package by.itechart.libmngmt.repository;

import java.util.List;

public interface GenreRepository {
    void add(String title);
    List<String> get();
    List<Integer> getId(Object[] genres);
    void addBookGenreRecord(int bookId, int genreId);
    void deleteBooksGenresRecords(int bookId);
}
