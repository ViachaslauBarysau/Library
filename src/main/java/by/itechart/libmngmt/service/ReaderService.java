package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderDto;

import java.util.Map;

public interface ReaderService {
    void add(ReaderDto readerDto);
    Map<Integer, ReaderDto> get(int bookId);
}
