package by.itechart.service;

import java.util.List;

public interface AuthorService {
    List<String> getListAuthorsByBookId(int bookId);
}
