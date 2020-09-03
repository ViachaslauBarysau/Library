package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;

import java.util.Map;

public interface ReaderRepository {
    Map<Integer, ReaderEntity> get(int bookId);
    void add(ReaderDto readerDto);
}
