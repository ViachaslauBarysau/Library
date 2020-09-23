package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.impl.AuthorRepositoryImpl;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.service.*;
import by.itechart.libmngmt.util.ConnectionHelper;
import by.itechart.libmngmt.util.converter.BookConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final static Logger logger = LogManager.getLogger(BookServiceImpl.class.getName());
    private final ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    private final BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private final AuthorService authorService = AuthorServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();
    private final CoverService coverService = CoverServiceImpl.getInstance();
    private static BookServiceImpl instance;

    public static BookServiceImpl getInstance() {
        if(instance == null){
            instance = new BookServiceImpl();
        }
        return instance;
    }

    @Override
    public List<BookDto> getBookPage(int pageNumber) {
        List<BookEntity> bookEntities = bookRepository.findAll(pageNumber);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntities) {
            bookDtoList.add(BookConverter.convertBookEntityToBookDto(bookEntity));
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
            bookDtoList.add(BookConverter.convertBookEntityToBookDto(bookEntity));
        }
        return bookDtoList;
    }

    @Override
    public void delete(List<Integer> booksList) {
        bookRepository.delete(booksList);
    }

    @Override
    public int getSearchPageCount(List<String> searchParams) {
        for (int index=0; index<searchParams.size(); index++){
            searchParams.set(index, "%" + searchParams.get(index).toLowerCase().trim() + "%");
        }
        return bookRepository.getSearchPageCount(searchParams);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        bookRepository.update(BookConverter.convertBookDtoToBookEntity(bookDto));
    }

    @Override
    public BookDto find(int bookId) {
        BookEntity bookEntity = bookRepository.find(bookId);
        BookDto bookDto = BookConverter.convertBookEntityToBookDto(bookEntity);
        return bookDto;
    }

    @Override
    public List<BookDto> search(List<String> searchParams, int pageNumber) {
        for (int index=0; index<searchParams.size(); index++) {
            searchParams.set(index, "%" + searchParams.get(index) + "%");
        }
        List<BookEntity> bookEntities = bookRepository.search(searchParams, pageNumber);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntities) {
            bookDtoList.add(BookConverter.convertBookEntityToBookDto(bookEntity));
        }
        return bookDtoList;
    }

    @Override
    public void updateBook(BookDto bookDto, Connection connection) throws SQLException {
        bookRepository.update(BookConverter.convertBookDtoToBookEntity(bookDto), connection);
    }

    @Override
    public int addEditBook(BookDto bookDto) {
        int bookId = 0;
        try (Connection connection = ConnectionHelper.getConnection()) {
            connection.setAutoCommit(false);
            if (bookDto.getId()==0) {
                bookDto.setAvailableAmount(bookDto.getTotalAmount());
                bookId = addBookGetId(bookDto, connection);
                bookDto.setId(bookId);
            } else {
                for (ReaderCardDto readerCardDto : bookDto.getReaderCardDtos()) {
                    readerCardService.addOrUpdateReaderCard(readerCardDto, connection);
                }
                int borrowedBooksAmount = readerCardService.getBorrowBooksCount(bookDto.getId());
                bookDto.setAvailableAmount(bookDto.getTotalAmount()-borrowedBooksAmount);
                updateBook(bookDto, connection);
                bookId = bookDto.getId();
            }
            authorService.add(bookDto, connection);
            genreService.add(bookDto, connection);
            coverService.add(bookDto, connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
             logger.debug("Adding/Editing book error!", e);
        }
        return bookId;
    }

    @Override
    public int addBookGetId(BookDto bookDto) {
        return bookRepository.add(BookConverter.convertBookDtoToBookEntity(bookDto));
    }

    @Override
    public int addBookGetId(BookDto bookDto, Connection connection) throws SQLException {
        return bookRepository.add(BookConverter.convertBookDtoToBookEntity(bookDto), connection);
    }
}
