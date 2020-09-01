package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.entity.Reader;

import java.util.List;
import java.util.Map;

public interface ReaderRepository {
    Map<Integer, Reader> get(int bookId);
}
