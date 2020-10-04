package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookService {
    List<BookDto> getAllBookPage(int pageNumber);

    List<BookDto> getAvailableBookPage(int pageNumber);

    int getAvailableBookPageCount();

    int getAllBookPageCount();

    int getSearchPageCount(List<String> searchParams);

    void delete(List<Integer> booksList);

    List<BookDto> search(List<String> searchParams, int pageNumber);

    int addEditBook(BookDto book);

    BookDto find(int bookId);
}
