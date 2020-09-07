package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderDto;

import java.util.List;
import java.util.Map;

public interface ReaderService {
    void add(ReaderDto readerDto);
    Map<Integer, ReaderDto> get(int bookId);
    List<String> getEmails(String pattern);
    String getNameByEmail(String email);
}
