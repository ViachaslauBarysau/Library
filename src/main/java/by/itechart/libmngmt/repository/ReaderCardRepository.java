package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;

import java.util.List;

public interface ReaderCardRepository {
    List<ReaderCardEntity> get(int bookId);
    void add(ReaderCardDto readerCardDto);
    void update(ReaderCardDto readerCardDto);
    ReaderCardEntity getReaderCard(int readerCardId);
}
