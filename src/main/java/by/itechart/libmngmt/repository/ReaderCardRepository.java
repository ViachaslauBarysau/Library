package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.entity.ReaderCardEntity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ReaderCardRepository {
    Date getNearestReturnDates(int bookId);
    List<ReaderCardEntity> getAllReaderCards(int bookId);
    List<ReaderCardEntity> getActiveReaderCards(int bookId);
    void add(ReaderCardEntity readerCard, Connection connection) throws SQLException;
    void update(ReaderCardEntity readerCard, Connection connection) throws SQLException;
    ReaderCardEntity getReaderCard(int readerCardId);
    int getActiveReaderCardsCount(int bookId);
    List<ReaderCardEntity> getExpiringReaderCards(int days);
}
