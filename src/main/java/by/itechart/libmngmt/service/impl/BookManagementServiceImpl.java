package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.entity.Reader;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.repository.impl.ReaderRepositoryImpl;
import by.itechart.libmngmt.service.BookManagementService;


public class BookManagementServiceImpl implements BookManagementService {

    private BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private ReaderRepository readerRepository = ReaderRepositoryImpl.getInstance();
    private ReaderCardRepository readerCardRepository = ReaderCardRepositoryImpl.getInstance();

    private static BookManagementServiceImpl instance = new BookManagementServiceImpl();

    public static BookManagementServiceImpl getInstance() {
        return instance;
    }

    @Override
    public BookPageDto getBookPageDto(int bookId) {
        BookPageDto bookPageDto = new BookPageDto(bookRepository.find(bookId), readerRepository.get(bookId), readerCardRepository.get(bookId));
        return bookPageDto;
    }
}
