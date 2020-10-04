package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.repository.impl.CoverRepositoryImpl;
import by.itechart.libmngmt.service.CoverService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides methods for operations with book covers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoverServiceImpl implements CoverService {
    private CoverRepository coverRepository = CoverRepositoryImpl.getInstance();
    private static volatile CoverServiceImpl instance;

    public static synchronized CoverServiceImpl getInstance() {
        CoverServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (CoverServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CoverServiceImpl();
                }
            }
        }
        return localInstance;
    }

    /**
     * Adds cover of specific book to the database. First, method deletes
     * old records about book cover, then adds new records there.
     *
     * @param bookDto    BookDto object
     * @param connection current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void add(final BookDto bookDto, final Connection connection) throws SQLException {
        List<String> bookCoversList = bookDto.getCovers();
        coverRepository.delete(bookDto.getId(), connection);
        for (String cover : bookCoversList) {
            coverRepository.add(bookDto.getId(), cover, connection);
        }
    }
}
