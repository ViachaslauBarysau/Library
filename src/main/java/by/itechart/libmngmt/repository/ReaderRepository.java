package by.itechart.libmngmt.repository;

import by.itechart.libmngmt.entity.ReaderEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ReaderRepository {
    void insertUpdate(ReaderEntity readerEntity, Connection connection) throws SQLException;

    int getId(String email, Connection connection) throws SQLException;

    List<String> getEmails(String searchParameter);

    String getName(String email);
}
