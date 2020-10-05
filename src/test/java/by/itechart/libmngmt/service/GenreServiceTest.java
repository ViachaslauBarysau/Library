package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.service.impl.GenreServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {
    private Connection connection;
    @Mock
    GenreRepository genreRepository;
    @InjectMocks
    GenreServiceImpl genreService;

    @Test
    public void testAdd() throws SQLException {
        // given
        String genre = "horror";
        int genreId = 0;
        int bookId = 0;
        List<Integer> genreIdList = Arrays.asList(genreId);
        BookDto bookDto = BookDto.builder()
                .id(bookId)
                .genres(Arrays.asList(genre))
                .build();
        when(genreRepository.getGenreIds(bookDto.getGenres(), connection)).thenReturn(genreIdList);
        // when
        genreService.add(bookDto, connection);
        // then
        verify(genreRepository).deleteBooksGenresRecords(bookDto.getId(), connection);
        verify(genreRepository).findAll(connection);
        verify(genreRepository).add(genre, connection);
        verify(genreRepository).getGenreIds(bookDto.getGenres(), connection);
        verify(genreRepository).addBookGenreRecord(bookDto.getId(), genreId, connection);
    }

    @Test(expected = SQLException.class)
    public void testAddThrowsException() throws SQLException {
        // given
        String genre = "horror";
        int genreId = 0;
        int bookId = 0;
        BookDto bookDto = BookDto.builder()
                .id(bookId)
                .genres(Arrays.asList(genre))
                .build();
        when(genreRepository.findAll(connection)).thenThrow(new SQLException());
        // when
        genreService.add(bookDto, connection);
        // then
        verify(genreRepository).deleteBooksGenresRecords(bookDto.getId(), connection);
        verify(genreRepository).findAll(connection);
        verify(genreRepository).add(genre, connection);
        verify(genreRepository).getGenreIds(bookDto.getGenres(), connection);
        verify(genreRepository).addBookGenreRecord(bookDto.getId(), genreId, connection);
    }
}
