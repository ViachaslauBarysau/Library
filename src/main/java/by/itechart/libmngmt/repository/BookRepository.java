package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

import java.util.List;

public interface BookRepository {
    List<BookEntity> get(int limitOffset);
    int getPageCount();
    void delete(Object[] bookList);
    BookEntity find(int bookId);
    List<BookEntity> search(List<String> searchParams, int limitOffset);
    int add(BookEntity book);
    void update(BookDto bookDto);
    int getSearchPageCount(List<String> searchParams);
}
