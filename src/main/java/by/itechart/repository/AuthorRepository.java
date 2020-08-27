package by.itechart.repository;

import java.util.List;

public interface AuthorRepository {
    List<String> getListAuthorsByBookId(int bookId);
}
