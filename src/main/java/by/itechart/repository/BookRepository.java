package by.itechart.repository;

import by.itechart.entity.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getPageOfBooks (int pageNumber);
    int getCountOfPages();
}
