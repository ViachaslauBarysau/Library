package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.service.converter.impl.BookDtoEntityConverter;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    private static final int ID = 0;
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_COUNT = 0;
    private Connection connection;
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;
    @Mock
    private CoverService coverService;
    @Mock
    private ReaderCardService readerCardService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookDtoEntityConverter bookConverter;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBookPage() {
        // given
        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        bookEntities.add(bookEntity);
        List<BookDto> expectedBookDtoList = new ArrayList<>();

        BookDto bookDto = new BookDto();
        expectedBookDtoList.add(bookDto);
        when(bookRepository.findAll(PAGE_NUMBER)).thenReturn(bookEntities);
        when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);
        // when
        List<BookDto> bookDtoList = bookService.getAllBookPage(PAGE_NUMBER);
        // then
        verify(bookRepository).findAll(PAGE_NUMBER);
        verify(bookConverter).convertToDto(bookEntity);
        assertEquals(expectedBookDtoList, bookDtoList);
    }

    @Test
    public void testGetAvailableBookPage() {
        // given
        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        bookEntities.add(new BookEntity());
        List<BookDto> expectedBookDtoList = new ArrayList<>();
        BookDto bookDto = new BookDto();
        expectedBookDtoList.add(bookDto);
        when(bookRepository.findAvailable(PAGE_NUMBER)).thenReturn(bookEntities);
        when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);
        // when
        List<BookDto> bookDtoList = bookService.getAvailableBookPage(PAGE_NUMBER);
        // then
        verify(bookRepository).findAvailable(PAGE_NUMBER);
        verify(bookConverter).convertToDto(bookEntity);
        assertEquals(expectedBookDtoList, bookDtoList);
    }

    @Test
    public void testGetPageCount() {
        // given
        when(bookRepository.getAllPageCount()).thenReturn(PAGE_COUNT);
        // when
        int pageCount = bookService.getAllBookPageCount();
        // then
        verify(bookRepository).getAllPageCount();
        assertEquals(PAGE_COUNT, pageCount);
    }

    @Test
    public void testGetAvailableBookPageCount() {
        // given
        when(bookRepository.getAvailableBookPageCount()).thenReturn(PAGE_COUNT);
        // when
        int pageCount = bookService.getAvailableBookPageCount();
        // then
        verify(bookRepository).getAvailableBookPageCount();
        assertEquals(PAGE_COUNT, pageCount);
    }

    @Test
    public void testDelete() {
        // given
        List<Integer> bookIdList = new ArrayList<>();
        // when
        bookService.delete(bookIdList);
        // then
        verify(bookRepository).delete(bookIdList);
    }

    @Test
    public void testGetSearchPageCount() {
        // given
        List<String> searchParams = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        List<String> refactoredSearchParams = new ArrayList<>(Arrays.asList("%a%", "%b%", "%c%", "%d%"));
        when(bookRepository.getSearchPageCount(refactoredSearchParams)).thenReturn(PAGE_COUNT);
        // when
        int pageCount = bookService.getSearchPageCount(searchParams);
        // then
        verify(bookRepository).getSearchPageCount(refactoredSearchParams);
        assertEquals(PAGE_COUNT, pageCount);
    }

    @Test
    public void testSearch() {
        // given
        List<BookEntity> bookEntityList = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        bookEntityList.add(bookEntity);
        List<BookDto> expectedBookDtoList = new ArrayList<>();
        BookDto bookDto = new BookDto();
        expectedBookDtoList.add(bookDto);
        List<String> searchParams = new ArrayList<>();
        when(bookRepository.search(searchParams, PAGE_NUMBER)).thenReturn(bookEntityList);
        when(bookConverter.convertToDto(bookEntity)).thenReturn(bookDto);
        // when
        List<BookDto> bookDtoList = bookService.search(searchParams, PAGE_NUMBER);
        // then
        verify(bookRepository).search(searchParams, PAGE_NUMBER);
        verify(bookConverter).convertToDto(bookEntity);
        assertEquals(expectedBookDtoList, bookDtoList);
    }


//    @Test
//    public void testUpdateBook() throws SQLException {
//        // given
//        BookDto bookDto = new BookDto();
//        BookEntity bookEntity = new BookEntity();
//        when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
//        // when
//        bookService.updateBook(bookDto, connection);
//        // then
//        verify(bookConverter).convertToEntity(bookDto);
//        verify(bookRepository).update(bookEntity, connection);
//    }

    @Test
    public void testFind() {
        // given
        BookDto expectedBookDto = new BookDto();
        BookEntity bookEntity = new BookEntity();
        when(bookRepository.find(ID)).thenReturn(bookEntity);
        when(bookConverter.convertToDto(bookEntity)).thenReturn(expectedBookDto);
        // when
        BookDto bookDto = bookService.find(ID);
        // then
        verify(bookConverter).convertToDto(bookEntity);
        verify(bookRepository).find(ID);
        assertEquals(expectedBookDto, bookDto);
    }

//    @Test
//    public void testAddBookGetId() throws SQLException {
//        // given
//        BookDto bookDto = new BookDto();
//        BookEntity bookEntity = new BookEntity();
//        when(bookRepository.add(bookEntity, connection)).thenReturn(ID);
//        when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
//        // when
//        int bookId = bookService.addBookGetId(bookDto, connection);
//        // then
//        verify(bookConverter).convertToEntity(bookDto);
//        verify(bookRepository).add(bookEntity, connection);
//        assertEquals(ID, bookId);
//    }

//    @Test(expected = SQLException.class)
//    public void testAddBookGetIdThrowsException() throws SQLException {
//        // given
//        BookDto bookDto = new BookDto();
//        BookEntity bookEntity = new BookEntity();
//        when(bookRepository.add(bookEntity, connection)).thenThrow(new SQLException());
//        when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
//        // when
//        int bookId = bookService.addBookGetId(bookDto, connection);
//        // then
//        verify(bookConverter).convertToEntity(bookDto);
//        verify(bookRepository).add(bookEntity, connection);
//        assertEquals(ID, bookId);
//    }

//    @Test(expected = SQLException.class)
//    public void testUpdateBookThrowsException() throws SQLException {
//        // given
//        BookDto bookDto = new BookDto();
//        BookEntity bookEntity = new BookEntity();
//        when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
//        doThrow(new SQLException()).when(bookRepository).update(bookEntity, connection);
//        // when
//        bookService.updateBook(bookDto, connection);
//        // then
//        verify(bookConverter).convertToEntity(bookDto);
//        verify(bookRepository).update(bookEntity, connection);
//    }

//    @Test(expected = SQLException.class)
//    public void testAddEditBookThrowsException() throws SQLException {
//        // given
//        BookDto bookDto = new BookDto();
//        BookEntity bookEntity = new BookEntity();
//        when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
//        when(bookRepository.add(bookEntity, connection)).thenThrow(new SQLException());
//        // when
//        int bookId = bookService.addBookGetId(bookDto, connection);
//        // then
//        verify(bookConverter).convertToEntity(bookDto);
//        verify(bookRepository).add(bookEntity, connection);
//        assertEquals(ID, bookId);
//    }

    @Test
    public void testAddEditBookZeroId() throws SQLException {
        // given
        int id = 0;
        BookDto bookDto = BookDto.builder()
                .id(ID)
                .build();
        BookEntity bookEntity = new BookEntity();
        when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
        when(bookRepository.add(any(), any())).thenReturn(ID);
        // when
        int bookId = bookService.addEditBook(bookDto);
        // then
        verify(bookConverter).convertToEntity(bookDto);
        verify(bookRepository).add(any(), any());
        verify(authorService).add(any(), any());
        verify(genreService).add(any(), any());
        verify(coverService).add(any(), any());
    }

    @Test
    public void testAddEditBookNonZeroId() throws SQLException {
        // given
        int id = 1;
        BookDto bookDto = BookDto.builder()
                .id(id)
                .readerCardDtos(Arrays.asList(new ReaderCardDto()))
                .build();
        BookEntity bookEntity = new BookEntity();
        when(bookConverter.convertToEntity(bookDto)).thenReturn(bookEntity);
        // when
        int bookId = bookService.addEditBook(bookDto);
        // then
        verify(bookConverter).convertToEntity(bookDto);
        verify(bookRepository).update(any(), any());
        verify(readerCardService).addOrUpdateReaderCard(any(), any());
        verify(authorService).add(any(), any());
        verify(genreService).add(any(), any());
        verify(coverService).add(any(), any());
    }
}
