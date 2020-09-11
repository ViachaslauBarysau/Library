package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.ReaderService;

import java.sql.Date;
import java.util.List;


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
        List<Date> nearestAvailableDateList = readerCardService.getNearestReturnDates(bookId);
        Date nearestAvailableDate = null;
        Date nextNearestAvailableDate = null;
        if (nearestAvailableDateList.size() > 0) {
            nearestAvailableDate = nearestAvailableDateList.get(0);
        }
        if (nearestAvailableDateList.size() == 2) {
            nextNearestAvailableDate = nearestAvailableDateList.get(1);
        }
        int nearestReturnReaderCardId = readerCardService.getNearestReturnReaderCardId(bookId);

//        request.setAttribute("nearestAvailableDateID", );
        BookPageDto bookPageDto = BookPageDto.builder()
                .bookDto(bookService.find(bookId))
                .readerCards(readerCardService.get(bookId))
                .nearestAvailableDate(nearestAvailableDate)
                .nextNearestAvailableDate(nextNearestAvailableDate)
                .nearestAvailableDateID(nearestReturnReaderCardId)
                .build();
        return bookPageDto;
    }
}
