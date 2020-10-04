package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ReaderService {
    List<String> getEmails(String pattern);

    String getNameByEmail(String email);

    int insertUpdateReaderGetId(ReaderDto readerDto, Connection connection) throws SQLException;
}
