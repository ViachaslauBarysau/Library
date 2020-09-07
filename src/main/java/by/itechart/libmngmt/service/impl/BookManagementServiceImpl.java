package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.repository.impl.ReaderRepositoryImpl;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.ReaderService;


public class BookManagementServiceImpl implements BookManagementService {
    private BookService bookService = BookServiceImpl.getInstance();
    private ReaderService readerService = ReaderServiceImpl.getInstance();
    private ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();

    private static BookManagementServiceImpl instance = new BookManagementServiceImpl();

    public static BookManagementServiceImpl getInstance() {
        return instance;
    }

    @Override
    public BookPageDto getBookPageDto(int bookId) {
        BookPageDto bookPageDto = new BookPageDto(bookService.find(bookId), readerCardService.get(bookId));
        return bookPageDto;
    }
}
