package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;

import java.util.List;
import java.util.Map;

public interface ReaderRepository {
    void insertUpdate(ReaderEntity readerEntity);
    int getId(String email);
    void add(ReaderEntity reader);
    List<String> getEmails(String searchParameter);
    String getName(String email);
}
