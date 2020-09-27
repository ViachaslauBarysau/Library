package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.repository.impl.CoverRepositoryImpl;
import by.itechart.libmngmt.service.CoverService;
import lombok.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {
        List<String> bookCoversList = bookDto.getCovers();
        coverRepository.delete(bookDto.getId(), connection);
        for (String cover : bookCoversList) {
            coverRepository.add(bookDto.getId(), cover, connection);
        }
    }
}
