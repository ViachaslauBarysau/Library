package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
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
public class BookManagementServiceTest {
    @Mock
    ReaderCardService readerCardService;
    @Mock
    BookService bookService;
    @InjectMocks
    BookManagementServiceImpl bookManagementService;

    @Test
    public void testGetNearestReturnDates() {
        // given
        int id = 0;
        BookDto bookDto = new BookDto();
        List<ReaderCardDto> readerCardDtoList = new ArrayList<>();
        BookPageDto expectedBookPageDto = new BookPageDto();
        expectedBookPageDto.setBookDto(bookDto);
        expectedBookPageDto.setReaderCards(readerCardDtoList);
        when(bookService.find(id)).thenReturn(bookDto);
        when(readerCardService.getAllReaderCards(id)).thenReturn(readerCardDtoList);
        // when
        BookPageDto bookPageDto = bookManagementService.getBookPageDto(id);
        // then
        verify(bookService).find(id);
        verify(readerCardService).getAllReaderCards(id);
        verify(readerCardService).getNearestReturnDates(id);
        assertEquals(expectedBookPageDto, bookPageDto);
    }
}
