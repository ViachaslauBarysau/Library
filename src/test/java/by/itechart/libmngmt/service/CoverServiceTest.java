package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.service.impl.CoverServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CoverServiceTest {
    private Connection connection;
    @Mock
    CoverRepository coverRepository;
    @InjectMocks
    CoverServiceImpl coverService;

    @Test
    public void testAdd() throws SQLException {
        // given
        String cover = "someCover";
        int bookId = 0;
        BookDto bookDto = BookDto.builder()
                .id(bookId)
                .covers(Arrays.asList(cover))
                .build();
        // when
        coverService.add(bookDto, connection);
        // then
        verify(coverRepository).delete(bookDto.getId(), connection);
        verify(coverRepository).add(bookDto.getId(), cover, connection);
    }

    @Test(expected = SQLException.class)
    public void testAddThrowsException() throws SQLException {
        // given
        String cover = "someCover";
        int bookId = 0;
        BookDto bookDto = BookDto.builder()
                .id(bookId)
                .covers(Arrays.asList(cover))
                .build();
        doThrow(new SQLException()).when(coverRepository).delete(bookDto.getId(), connection);
        // when
        coverService.add(bookDto, connection);
        // then
        verify(coverRepository).delete(bookDto.getId(), connection);
        verify(coverRepository).add(bookDto.getId(), cover, connection);
    }
}
