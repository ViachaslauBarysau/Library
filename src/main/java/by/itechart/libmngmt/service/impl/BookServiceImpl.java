package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.service.*;
import by.itechart.libmngmt.service.converter.Converter;
import by.itechart.libmngmt.service.converter.ConverterFactory;
import by.itechart.libmngmt.util.ConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides methods for operations with book.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class.getName());
    private static final int BOOK_DTO_ENTITY_CONVERTER_TYPE = 1;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Converter<BookDto, BookEntity> bookDtoEntityConverterConverter
            = ConverterFactory.getInstance().createConverter(BOOK_DTO_ENTITY_CONVERTER_TYPE);
    private ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    private BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private AuthorService authorService = AuthorServiceImpl.getInstance();
    private GenreService genreService = GenreServiceImpl.getInstance();
    private CoverService coverService = CoverServiceImpl.getInstance();
    private static volatile BookServiceImpl instance;

    public static synchronized BookServiceImpl getInstance() {
        BookServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (BookServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BookServiceImpl();
                }
            }
        }
        return localInstance;
    }

    /**
     * Returns list of books gets by page number.
     *
     * @param pageNumber ID of book
     * @return list of BookDto
     */
    @Override
    public List<BookDto> getAllBookPage(final int pageNumber) {
        return bookRepository.findAll(pageNumber).stream()
                .map(bookEntity -> bookDtoEntityConverterConverter.convertToDto(bookEntity))
                .collect(Collectors.toList());
    }

    /**
     * Returns count of pages with all books.
     *
     * @return count of pages
     */
    @Override
    public int getAllBookPageCount() {
        return bookRepository.getAllPageCount();
    }

    /**
     * Adds new or edits existing book and returns its ID.
     * It checks book ID and then invokes add(ID=0) or update methods
     * and calls for another services to add book info like cover etc.
     *
     * @param bookDto BookDto object for adding/editing
     * @return book ID
     */
    @Override
    public int addEditBook(final BookDto bookDto) {
        int bookId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            boolean isBookNew = bookDto.getId() == 0;
            if (isBookNew) {
                bookDto.setAvailableAmount(bookDto.getTotalAmount());
                bookId = addBookGetId(bookDto, connection);
                bookDto.setId(bookId);
            } else {
                for (ReaderCardDto readerCardDto : bookDto.getReaderCardDtos()) {
                    readerCardService.addOrUpdateReaderCard(readerCardDto, connection);
                }
                updateBook(bookDto, connection);
                bookId = bookDto.getId();
            }
            authorService.add(bookDto, connection);
            genreService.add(bookDto, connection);
            coverService.add(bookDto, connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.debug("Adding/Editing book error.", e);
        }
        return bookId;
    }

    /**
     * Returns count of pages without unavailable books.
     *
     * @return count of pages
     */
    @Override
    public int getAvailableBookPageCount() {
        return bookRepository.getAvailableBookPageCount();
    }

    /**
     * Returns list of available books by page number.
     *
     * @param pageNumber ID of book
     * @return list of BookDto
     */
    @Override
    public List<BookDto> getAvailableBookPage(final int pageNumber) {
        return bookRepository.findAvailable(pageNumber).stream()
                .map(bookEntity -> bookDtoEntityConverterConverter.convertToDto(bookEntity))
                .collect(Collectors.toList());
    }

    /**
     * Deletes books by IDs.
     *
     * @param booksList list with book IDs
     */
    @Override
    public void delete(final List<Integer> booksList) {
        bookRepository.delete(booksList);
    }

    /**
     * Returns count of pages with search results.
     *
     * @param searchParams list with search parameters
     * @return count of pages
     */
    @Override
    public int getSearchPageCount(final List<String> searchParams) {
        return bookRepository.getSearchPageCount(refactorSearchParams(searchParams));
    }

    /**
     * Return book searched by its ID.
     *
     * @param bookId book ID
     * @return BookDto object
     */
    @Override
    public BookDto find(final int bookId) {
        return bookDtoEntityConverterConverter.convertToDto(bookRepository.find(bookId));
    }

    /**
     * Returns list of books searched by search parameters(for one specific page).
     * Search parameters are changed here, so no need to refactor them before.
     *
     * @param searchParams list with search parameters
     * @param pageNumber   page number
     * @return list of BookDto
     */
    @Override
    public List<BookDto> search(final List<String> searchParams, final int pageNumber) {
        return bookRepository.search(refactorSearchParams(searchParams), pageNumber).stream()
                .map(bookEntity -> bookDtoEntityConverterConverter.convertToDto(bookEntity))
                .collect(Collectors.toList());
    }

    private void updateBook(final BookDto bookDto, final Connection connection) throws SQLException {
        bookRepository.update(bookDtoEntityConverterConverter.convertToEntity(bookDto), connection);
    }

    private int addBookGetId(final BookDto bookDto, final Connection connection) throws SQLException {
        return bookRepository.add(bookDtoEntityConverterConverter.convertToEntity(bookDto), connection);
    }

    private List<String> refactorSearchParams(final List<String> searchParams) {
        List<String> refactoredSearchParams = new ArrayList<>();
        for (String parameter : searchParams) {
            refactoredSearchParams.add("%" + parameter.toLowerCase().trim() + "%");
        }
        return refactoredSearchParams;
    }
}
