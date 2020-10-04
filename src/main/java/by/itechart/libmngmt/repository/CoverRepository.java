package by.itechart.libmngmt.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface CoverRepository {

    void add(int bookId, String title, Connection connection) throws SQLException;

    void delete(int bookId, Connection connection) throws SQLException;
}
