package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface ReaderService {
    void insertUpdateReader(ReaderDto readerDto);
    List<String> getEmails(String pattern);
    String getNameByEmail(String email);
    int insertUpdateReaderGetId(ReaderDto readerDto, Connection connection);
    int getIdByEmail(String email);
}
