package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.service.ReaderCardService;

import java.sql.Date;
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
                .status(readerCardEntity.getStatus())
                .dueDate(readerCardEntity.getDueDate())
                .returnDate(readerCardEntity.getReturnDate())
                .comment(readerCardEntity.getComment())
                .build());

        }
        return readerCardDtos;
    }

    @Override
    public List<Date> getNearestReturnDates(int bookId) {
        return readerCardRepository.getNearestReturnDates(bookId);
    }

    @Override
    public int getNearestReturnReaderCardId(int bookId) {
        return readerCardRepository.getNearestReturnReaderCardId(bookId);
    }

    @Override
    public ReaderCardDto getReaderCard(int readerCardId) {
        ReaderCardEntity readerCardEntity = readerCardRepository.getReaderCard(readerCardId);
        ReaderCardDto readerCardDto = ReaderCardDto.builder()
                .id(readerCardEntity.getId())
                .bookId(readerCardEntity.getBookId())
                .readerId(readerCardEntity.getReaderId())
                .readerName(readerCardEntity.getReaderName())
                .readerEmail(readerCardEntity.getReaderEmail())
                .borrowDate(readerCardEntity.getBorrowDate())
                .status(readerCardEntity.getStatus())
                .dueDate(readerCardEntity.getDueDate())
                .returnDate(readerCardEntity.getReturnDate())
                .comment(readerCardEntity.getComment())
                .build();
        if (readerCardEntity.getComment() == null) {
            readerCardDto.setComment("");
        }
        return readerCardDto;
    }

    @Override
    public void add(ReaderCardEntity readerCard) {
        readerCardRepository.add(readerCard);
    }

    @Override
    public void update(ReaderCardEntity readerCard) {
        readerCardRepository.update(readerCard);
    }


}
