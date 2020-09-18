package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;

import java.sql.Date;
import java.util.List;


public interface ReaderCardService {
    void add(ReaderCardDto readerCard);
    void update(ReaderCardDto readerCard);
    void addOrUpdateReaderCard(ReaderCardDto readerCard);
    List<ReaderCardDto> get(int bookId);
    ReaderCardDto getReaderCard(int readerCardId);
    List<Date> getNearestReturnDates(int bookId);
    int getNearestReturnReaderCardId(int bookId);
    int getBorrowBooksCount(int bookId);
    List<ReaderCardDto> getExpiringReaderCards(int days);
}
