package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.entity.ReaderCardEntity;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface ReaderCardRepository {
    int getNearestReturnReaderCardId(int bookId);
    List<Date> getNearestReturnDates(int bookId);
    List<ReaderCardEntity> get(int bookId);
    void add(ReaderCardEntity readerCard);
    void update(ReaderCardEntity readerCard);
    ReaderCardEntity getReaderCard(int readerCardId);
}
