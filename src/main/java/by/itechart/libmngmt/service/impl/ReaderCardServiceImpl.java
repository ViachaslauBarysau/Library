package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.service.ReaderCardService;

import java.util.ArrayList;
import java.util.List;

public class ReaderCardServiceImpl implements ReaderCardService {

    private ReaderCardRepository readerCardRepository = ReaderCardRepositoryImpl.getInstance();
    private static ReaderCardServiceImpl instance = new ReaderCardServiceImpl();

    public static ReaderCardServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<ReaderCardDto> get(int bookId) {
        List<ReaderCardEntity> readerCardEntities = readerCardRepository.get(bookId);
        List<ReaderCardDto> readerCardDtos = new ArrayList<>();
        for (ReaderCardEntity readerCardEntity:readerCardEntities
             ) {readerCardDtos.add(ReaderCardDto.builder()
                .id(readerCardEntity.getId())
                .bookId(readerCardEntity.getBookId())
                .readerId(readerCardEntity.getReaderId())
                .readerName(readerCardEntity.getReaderName())
                .readerEmail(readerCardEntity.getReaderEmail())
                .borrowDate(readerCardEntity.getBorrowDate())
                .timePeriod(readerCardEntity.getTimePeriod())
                .dueDate(readerCardEntity.getDueDate())
                .returnDate(readerCardEntity.getReturnDate())
                .build());

        }
        return readerCardDtos;
    }

    @Override
    public void add(ReaderCardDto readerCardDto) {

    }

    @Override
    public void update(ReaderCardDto readerCardDto) {

    }


}
