package by.itechart.libmngmt.service.impl;


import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.repository.CoverRepository;
import by.itechart.libmngmt.repository.impl.CoverRepositoryImpl;
import by.itechart.libmngmt.service.CoverService;

import java.util.List;

public class CoverServiceImpl implements CoverService {
    private CoverRepository coverRepository = CoverRepositoryImpl.getInstance();
    private static CoverServiceImpl instance = new CoverServiceImpl();

    public static CoverServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void add(BookDto bookDto) {
        List<String> bookCoversList = bookDto.getCovers();
        coverRepository.delete(bookDto.getId());
        for (int index=0; index<bookCoversList.size(); index++) {
            coverRepository.add(bookDto.getId(), bookCoversList.get(index));
        }
    }
}
