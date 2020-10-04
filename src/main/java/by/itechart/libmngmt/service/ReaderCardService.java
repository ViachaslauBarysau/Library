package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderCardDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public interface ReaderCardService {
    void addOrUpdateReaderCard(ReaderCardDto readerCard, Connection connection) throws SQLException;

    List<ReaderCardDto> getAllReaderCards(int bookId);

    List<ReaderCardDto> getActiveReaderCards(int bookId);

    ReaderCardDto getReaderCard(int readerCardId);

    Date getNearestReturnDates(int bookId);

    List<ReaderCardDto> getExpiringReaderCards(int days);
}
