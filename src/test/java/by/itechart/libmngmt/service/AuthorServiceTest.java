package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.service.impl.AuthorServiceImpl;
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
public class AuthorServiceTest {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Mock
    AuthorRepository authorRepository;
    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void testAdd() throws SQLException {
        // given
        String author = "Leo Tolstoi";
        int id = 0;
        List<String> authorList = Arrays.asList(author);
        List<Integer> idList = Arrays.asList(id);
        BookDto bookDto = BookDto.builder()
                .id(anyInt())
                .authors(Arrays.asList(author))
                .build();
        Connection connection = connectionPool.getConnection();
        when(authorRepository.findAll(connection)).thenReturn(authorList);
        when(authorRepository.getAuthorIds(bookDto.getAuthors(), connection)).thenReturn(idList);
        // when
        authorService.add(bookDto, connection);
        // then
        verify(authorRepository).deleteBooksAuthorsRecords(bookDto.getId(), connection);
        verify(authorRepository).findAll(connection);
        verify(authorRepository).add(author, connection);
        verify(authorRepository).getAuthorIds(bookDto.getAuthors(), connection);
        verify(authorRepository).addBookAuthorRecord(bookDto.getId(), id, connection);
    }
}
