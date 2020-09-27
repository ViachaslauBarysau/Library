package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.GenreRepository;
import by.itechart.libmngmt.service.impl.GenreServiceImpl;
import by.itechart.libmngmt.util.ConnectionPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Mock
    GenreRepository genreRepository;
    @InjectMocks
    GenreServiceImpl genreService;

    @Test
    public void testAdd() throws SQLException {
        // given
        String genre = "horror";
        int id = 0;
        List<String> genreList = Arrays.asList(genre);
        List<Integer> idList = Arrays.asList(id);
        BookDto bookDto = BookDto.builder()
                .id(anyInt())
                .genres(Arrays.asList(genre))
                .build();
        Connection connection = connectionPool.getConnection();
        when(genreRepository.findAll(connection)).thenReturn(genreList);
        when(genreRepository.getGenreIds(bookDto.getGenres(), connection)).thenReturn(idList);
        // when
        genreService.add(bookDto, connection);
        // then
        verify(genreRepository).deleteBooksGenresRecords(bookDto.getId(), connection);
        verify(genreRepository).findAll(connection);
        verify(genreRepository).add(genre, connection);
        verify(genreRepository).getGenreIds(bookDto.getGenres(), connection);
        verify(genreRepository).addBookGenreRecord(bookDto.getId(), id, connection);
    }
}
