package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.entity.Book;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.service.BookService;


import java.util.List;

public class BookServiceImpl implements BookService {
    private BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private static BookServiceImpl instance = new BookServiceImpl();

    public static BookServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Book> getPageOfBooks(int pageNumber) {
        return bookRepository.get(pageNumber);
    }

    @Override
    public int getCountOfPages() {
        return bookRepository.getPageCount();
    }

    @Override
    public void deleteBooks(Object[] booksList) {
        bookRepository.delete(booksList);
    }

    @Override
    public List<Book> searchBooks(List<String> searchParams) {

        for (int index=0; index<searchParams.size(); index++){
            searchParams.set(index, "%" + searchParams.get(index) + "%");
        }
        return bookRepository.search(searchParams);

    }

    @Override
    public void addBook(Book book) {
        bookRepository.add(book);
    }

    @Override
    public Book getBook(int bookId) {
        return bookRepository.find(bookId);
    }
}
