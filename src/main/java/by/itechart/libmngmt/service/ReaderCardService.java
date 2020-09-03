package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;

import java.util.List;

public interface ReaderCardService {
    void add(ReaderCardDto readerCardDto);
    void update(ReaderCardDto readerCardDto);
    List<ReaderCardDto> get(int bookId);

}
