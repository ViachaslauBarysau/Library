package by.itechart.service;

import by.itechart.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getPageOfBooks (int pageNumber);
    int getCountOfPages();
    void deleteBooks(Object[] booksList);
    Book getBook(int bookId);
    List<Book> searchBooks(List<String> searchParams);
}
