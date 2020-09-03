package by.itechart.libmngmt.repository;

import java.util.List;

public interface AuthorRepository {
    void add(String name);
    List<String> get();
    List<Integer> getId(Object[] names);
    void addBookAuthorRecord(int bookId, int authorId);
    void deleteBooksAuthorsRecords(int bookId);
}
