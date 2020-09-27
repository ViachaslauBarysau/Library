package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


public interface ReaderCardService {
    void add(ReaderCardDto readerCard, Connection connection) throws SQLException;
    void update(ReaderCardDto readerCard, Connection connection) throws SQLException;
    void addOrUpdateReaderCard(ReaderCardDto readerCard, Connection connection) throws SQLException;
    List<ReaderCardDto> getAllReaderCards(int bookId);
    List<ReaderCardDto> getActiveReaderCards(int bookId);
    ReaderCardDto getReaderCard(int readerCardId);
    Date getNearestReturnDates(int bookId);
    List<ReaderCardDto> getExpiringReaderCards(int days);
}
