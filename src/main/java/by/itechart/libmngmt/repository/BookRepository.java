package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.entity.Book;

import java.util.List;

public interface BookRepository {
    List<Book> get(int limitOffset);
    int getPageCount();
    void delete(Object[] bookList);
    Book find(int bookId);
    List<Book> search(List<String> searchParams);
    int add(Book book);
}
