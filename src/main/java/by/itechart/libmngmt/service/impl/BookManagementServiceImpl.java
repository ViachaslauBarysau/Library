package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;

import java.sql.Date;

public class BookManagementServiceImpl implements BookManagementService {
    private final BookService bookService = BookServiceImpl.getInstance();
    private final ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();

    private static BookManagementServiceImpl instance;

    public static BookManagementServiceImpl getInstance() {
        if(instance == null){
            instance = new BookManagementServiceImpl();
        }
        return instance;
    }

    @Override
    public BookPageDto getBookPageDto(int bookId) {
        Date nearestAvailableDate = readerCardService.getNearestReturnDates(bookId);
        BookPageDto bookPageDto = BookPageDto.builder()
                .bookDto(bookService.find(bookId))
                .readerCards(readerCardService.get(bookId))
                .nearestAvailableDate(nearestAvailableDate)
                .build();
        return bookPageDto;
    }
}
