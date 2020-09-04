package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.service.AuthorService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.CoverService;
import by.itechart.libmngmt.service.GenreService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private AuthorService authorService = AuthorServiceImpl.getInstance();
    private GenreService genreService = GenreServiceImpl.getInstance();
    private CoverService coverService = CoverServiceImpl.getInstance();
    private static BookServiceImpl instance = new BookServiceImpl();

    public static BookServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<BookEntity> getBookPage(int pageNumber) {
        return bookRepository.get(pageNumber);
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
    public void addBook(BookEntity bookEntity) {

        int bookId = addBookGetId(bookEntity);

        BookDto bookDto = BookDto.builder().id(bookId)
                .authors(bookEntity.getAuthors())
                .covers(bookEntity.getCovers())
                .genres(bookEntity.getGenres())
                .build();

        authorService.add(bookDto);
        genreService.add(bookDto);
        coverService.add(bookDto);

    }


    @Override
    public int addBookGetId(BookEntity book) {
        return bookRepository.add(book);
    }

}
