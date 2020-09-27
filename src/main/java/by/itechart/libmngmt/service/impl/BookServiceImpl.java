package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.service.*;
import by.itechart.libmngmt.util.ConnectionPool;
import by.itechart.libmngmt.util.converter.BookConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class.getName());
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private BookConverter bookConverter = BookConverter.getInstance();
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

    @Override
    public List<BookDto> getBookPage(int pageNumber) {
        List<BookEntity> bookEntities = bookRepository.findAll(pageNumber);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntities) {
            bookDtoList.add(bookConverter.convertBookEntityToBookDto(bookEntity));
        }
        return bookDtoList;
    }

    @Override
    public int getPageCount() {
        return bookRepository.getPageCount();
    }

    @Override
    public int getAvailableBookPageCount() {
        return bookRepository.getAvailableBookPageCount();
    }

    @Override
    public List<BookDto> getAvailableBookPage(int pageNumber) {
        List<BookEntity> bookEntities = bookRepository.findAvailable(pageNumber);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntities) {
            bookDtoList.add(bookConverter.convertBookEntityToBookDto(bookEntity));
        }
        return bookDtoList;
    }

    @Override
    public void delete(List<Integer> booksList) {
        bookRepository.delete(booksList);
    }

    @Override
    public int getSearchPageCount(List<String> searchParams) {
        List<String> refactoredSearchParams = refactorSearchParams(searchParams);
        return bookRepository.getSearchPageCount(refactoredSearchParams);
    }

    @Override
    public BookDto find(int bookId) {
        BookEntity bookEntity = bookRepository.find(bookId);
        BookDto bookDto = bookConverter.convertBookEntityToBookDto(bookEntity);
        return bookDto;
    }

    @Override
    public List<BookDto> search(List<String> searchParams, int pageNumber) {
        List<String> refactoredSearchParams = refactorSearchParams(searchParams);
        List<BookEntity> bookEntities = bookRepository.search(refactoredSearchParams, pageNumber);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntities) {
            bookDtoList.add(bookConverter.convertBookEntityToBookDto(bookEntity));
        }
        return bookDtoList;
    }

    @Override
    public void updateBook(BookDto bookDto, Connection connection) throws SQLException {
        bookRepository.update(bookConverter.convertBookDtoToBookEntity(bookDto), connection);
    }

    @Override
    public int addEditBook(BookDto bookDto) {
        int bookId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            if (bookDto.getId() == 0) {
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

    @Override
    public int addBookGetId(BookDto bookDto, Connection connection) throws SQLException {
        return bookRepository.add(bookConverter.convertBookDtoToBookEntity(bookDto), connection);
    }

    private List<String> refactorSearchParams(List<String> searchParams) {
        List<String> refactoredSearchParams = new ArrayList<>();
        for (String parameter : searchParams) {
            refactoredSearchParams.add("%" + parameter.toLowerCase().trim() + "%");
        }
        return refactoredSearchParams;
    }
}
