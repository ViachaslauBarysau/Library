package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.service.impl.AuthorServiceImpl;
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
public class AuthorServiceTest {
    private Connection connection;
    @Mock
    AuthorRepository authorRepository;
    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void testAdd() throws SQLException {
        // given
        String author = "Leo Tolstoi";
        int genreId = 0;
        int bookId = 0;
        List<Integer> authorIdList = Arrays.asList(genreId);
        BookDto bookDto = BookDto.builder()
                .id(bookId)
                .authors(Arrays.asList(author))
                .build();
        when(authorRepository.getAuthorIds(bookDto.getAuthors(), connection)).thenReturn(authorIdList);
        // when
        authorService.add(bookDto, connection);
        // then
        verify(authorRepository).deleteBooksAuthorsRecords(bookDto.getId(), connection);
        verify(authorRepository).findAll(connection);
        verify(authorRepository).add(author, connection);
        verify(authorRepository).getAuthorIds(bookDto.getAuthors(), connection);
        verify(authorRepository).addBookAuthorRecord(bookDto.getId(), genreId, connection);
    }

    @Test(expected = SQLException.class)
    public void testAddThrowsException() throws SQLException {
        // given
        String author = "Leo Tolstoi";
        int genreId = 0;
        int bookId = 0;
        List<Integer> authorIdList = Arrays.asList(genreId);
        BookDto bookDto = BookDto.builder()
                .id(bookId)
                .authors(Arrays.asList(author))
                .build();
        when(authorRepository.getAuthorIds(bookDto.getAuthors(), connection)).thenThrow(new SQLException());
        // when
        authorService.add(bookDto, connection);
        // then
        verify(authorRepository).deleteBooksAuthorsRecords(bookDto.getId(), connection);
        verify(authorRepository).findAll(connection);
        verify(authorRepository).add(author, connection);
        verify(authorRepository).getAuthorIds(bookDto.getAuthors(), connection);
        verify(authorRepository).addBookAuthorRecord(bookDto.getId(), genreId, connection);
    }
}
