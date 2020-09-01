package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.entity.ReaderCard;

import java.util.List;

public interface ReaderCardRepository {
    List<ReaderCard> get(int bookId);
}
