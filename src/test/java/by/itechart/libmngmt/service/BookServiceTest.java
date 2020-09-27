package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.util.converter.BookConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    private static final int ID = 0;
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_COUNT = 0;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookConverter bookConverter;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetBookPage() {
        // given
        List<BookEntity> bookEntities = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        bookEntities.add(bookEntity);
        List<BookDto> expectedBookDtoList = new ArrayList<>();
        BookDto bookDto = new BookDto();
        expectedBookDtoList.add(bookDto);
        when(bookRepository.findAll(PAGE_NUMBER)).thenReturn(bookEntities);
        when(bookConverter.convertBookEntityToBookDto(bookEntity)).thenReturn(bookDto);
        // when
        List<BookDto> bookDtos = bookService.getBookPage(PAGE_NUMBER);
        // then
        verify(bookRepository).findAll(PAGE_NUMBER);
        verify(bookConverter).convertBookEntityToBookDto(bookEntity);
        assertEquals(expectedBookDtoList, bookDtos);
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
        when(bookConverter.convertBookEntityToBookDto(bookEntity)).thenReturn(bookDto);
        // when
        List<BookDto> bookDtos = bookService.getAvailableBookPage(PAGE_NUMBER);
        // then
        verify(bookRepository).findAvailable(PAGE_NUMBER);
        verify(bookConverter).convertBookEntityToBookDto(bookEntity);
        assertEquals(expectedBookDtoList, bookDtos);
    }

    @Test
    public void testGetPageCount() {
        // given
        when(bookRepository.getPageCount()).thenReturn(PAGE_COUNT);
        // when
        int pageCount = bookService.getPageCount();
        // then
        verify(bookRepository).getPageCount();
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
        List<String> searchParams = new ArrayList<>();
        when(bookRepository.getSearchPageCount(searchParams)).thenReturn(PAGE_COUNT);
        // when
        int pageCount = bookService.getSearchPageCount(searchParams);
        // then
        verify(bookRepository).getSearchPageCount(searchParams);
        assertEquals(PAGE_COUNT, pageCount);
    }
}
