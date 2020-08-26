package by.itechart.service;

import by.itechart.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getPageOfBooks (int pageNumber);
    int getCountOfPages();
}
