package by.itechart.repository;

import by.itechart.entity.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getBooks(int pageNumber);
    int getCountOfPages();
    void deleteBooks(Object[] bookList);
}
