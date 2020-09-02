package by.itechart.libmngmt.service.impl;


import by.itechart.libmngmt.dto.BookAddDto;
import by.itechart.libmngmt.repository.BookRepository;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import by.itechart.libmngmt.repository.impl.CoverRepositoryImpl;
import by.itechart.libmngmt.service.CoverService;

public class CoverServiceImpl implements CoverService {
    private CoverRepository coverRepository = CoverRepositoryImpl.getInstance();
    private static CoverServiceImpl instance = new CoverServiceImpl();

    public static CoverServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void add(BookAddDto bookAddDto) {

    }
}
