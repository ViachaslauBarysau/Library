package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookDto;

import java.sql.Connection;
import java.sql.SQLException;

public interface AuthorService {
    void add(BookDto bookDto);
    void add(BookDto bookDto, Connection connection) throws SQLException;
}
