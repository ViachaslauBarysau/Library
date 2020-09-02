package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.BookAddDto;

import java.util.List;

public interface AuthorRepository {
    void add(String name);
    List<String> get();
    List<Integer> getId(Object[] names);
    void addBookAuthor(int bookId, int authorId);
}
