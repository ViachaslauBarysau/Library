package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookManagementServiceImpl implements BookManagementService {
    private BookService bookService = BookServiceImpl.getInstance();
    private ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    private static volatile BookManagementServiceImpl instance;

    public static synchronized BookManagementServiceImpl getInstance() {
        BookManagementServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (BookManagementServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BookManagementServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public BookPageDto getBookPageDto(int bookId) {
        BookPageDto bookPageDto = BookPageDto.builder()
                .bookDto(bookService.find(bookId))
                .readerCards(readerCardService.getAllReaderCards(bookId))
                .nearestAvailableDate(readerCardService.getNearestReturnDates(bookId))
                .build();
        return bookPageDto;
    }
}
