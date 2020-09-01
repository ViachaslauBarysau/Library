package by.itechart.libmngmt.service;

import by.itechart.libmngmt.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getPageOfBooks (int pageNumber);
    int getCountOfPages();
    void deleteBooks(Object[] booksList);
    Book getBook(int bookId);
    List<Book> searchBooks(List<String> searchParams);
    void addBook(Book book);
}
