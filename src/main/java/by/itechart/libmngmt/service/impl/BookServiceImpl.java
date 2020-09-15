package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.service.*;
import by.itechart.libmngmt.util.ConnectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {
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
    public List<BookEntity> getBookPage(int pageNumber) {
        return bookRepository.findAll(pageNumber);
    }

    @Override
    public int getPageCount() {
        return bookRepository.getPageCount();
    }

    @Override
    public void delete(Object[] booksList) {
        bookRepository.delete(booksList);
    }

    @Override
    public int getSearchPageCount(List<String> searchParams) {

        for (int index=0; index<searchParams.size(); index++){
            searchParams.set(index, "%" + searchParams.get(index) + "%");
        }

        return bookRepository.getSearchPageCount(searchParams);
    }

    @Override
    public void updateBook(BookEntity book) {
        bookRepository.update(book);
    }

    @Override
    public BookDto find(int bookId) {
        BookEntity bookEntity = bookRepository.find(bookId);

        BookDto bookDto = BookDto.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .publisher(bookEntity.getPublisher())
                .publishDate(bookEntity.getPublishDate())
                .ISBN(bookEntity.getISBN())
                .description(bookEntity.getDescription())
                .totalAmount(bookEntity.getTotalAmount())
                .pageCount(bookEntity.getPageCount())
                .availableAmount(bookEntity.getAvailableAmount())
                .authors(bookEntity.getAuthors())
                .genres(bookEntity.getGenres())
                .covers(bookEntity.getCovers())
                .build();
        return bookDto;
    }

    @Override
    public List<BookEntity> search(List<String> searchParams, int pageNumber) {

        for (int index=0; index<searchParams.size(); index++){
            searchParams.set(index, "%" + searchParams.get(index) + "%");
        }
        return bookRepository.search(searchParams, pageNumber);

    }

    @Override
    public void updateBook(BookEntity book, Connection connection) throws SQLException {
        bookRepository.update(book, connection);
    }



    @Override
    public int addEditBook(BookEntity book) {

        int bookId = 0;
        try (Connection connection = ConnectionHelper.getConnection()) {
            connection.setAutoCommit(false);
            if (book.getId()==0) {
                bookId = addBookGetId(book, connection);
            } else {
                int borrowedBooksAmount = readerCardService.getBorrowBooksCount(book.getId());
                book.setAvailableAmount(book.getTotalAmount()-borrowedBooksAmount);
                updateBook(book, connection);
                bookId = book.getId();
            }
            BookDto bookDto = BookDto.builder().id(bookId)
                    .authors(book.getAuthors())
                    .covers(book.getCovers())
                    .genres(book.getGenres())
                    .build();

            authorService.add(bookDto, connection);
            genreService.add(bookDto, connection);
            coverService.add(bookDto, connection);
            connection.commit();

            connection.setAutoCommit(true);

        }
             catch (SQLException e) {
            e.printStackTrace();
        }
        return bookId;
    }


    @Override
    public int addBookGetId(BookEntity book) {
        return bookRepository.add(book);
    }

    @Override
    public int addBookGetId(BookEntity book, Connection connection) throws SQLException {
        return bookRepository.add(book, connection);
    }


}
