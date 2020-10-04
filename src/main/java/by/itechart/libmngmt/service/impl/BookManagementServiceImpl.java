package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Provides methods for operations with BookPageDto.
 */
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

    /**
     * Returns BookPageDto object needed for book page. Consists of BookDto,
     * list of ReaderCards and nearest available date of specific Book.
     *
     * @param bookId ID of book
     * @return BookPageDto object
     */
    @Override
    public BookPageDto getBookPageDto(final int bookId) {
        return BookPageDto.builder()
                .bookDto(bookService.find(bookId))
                .readerCards(readerCardService.getAllReaderCards(bookId))
                .nearestAvailableDate(readerCardService.getNearestReturnDates(bookId))
                .build();
    }
}
