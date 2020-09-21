package by.itechart.libmngmt.service.impl;


import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.repository.impl.CoverRepositoryImpl;
import by.itechart.libmngmt.service.CoverService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CoverServiceImpl implements CoverService {
    private final CoverRepository coverRepository = CoverRepositoryImpl.getInstance();
    private static CoverServiceImpl instance;

    public static CoverServiceImpl getInstance() {
        if(instance == null){
            instance = new CoverServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(BookDto bookDto) {
        List<String> bookCoversList = bookDto.getCovers();
        coverRepository.delete(bookDto.getId());
        for (String cover: bookCoversList) {
            coverRepository.add(bookDto.getId(), cover);
        }
    }

    @Override
    public void add(BookDto bookDto, Connection connection) throws SQLException {
        List<String> bookCoversList = bookDto.getCovers();
        coverRepository.delete(bookDto.getId(), connection);
        for (String cover: bookCoversList) {
            coverRepository.add(bookDto.getId(), cover, connection);
        }
    }
}
