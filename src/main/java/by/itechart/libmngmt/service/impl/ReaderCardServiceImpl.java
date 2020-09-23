package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.util.converter.BookConverter;
import by.itechart.libmngmt.util.converter.ReaderCardConverter;

import javax.faces.convert.BooleanConverter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderCardServiceImpl implements ReaderCardService {
    private final ReaderService readerService = ReaderServiceImpl.getInstance();
    private final ReaderCardRepository readerCardRepository = ReaderCardRepositoryImpl.getInstance();
    private static ReaderCardServiceImpl instance;

    public static ReaderCardServiceImpl getInstance() {
        if(instance == null){
            instance = new ReaderCardServiceImpl();
        }
        return instance;
    }

    @Override
    public List<ReaderCardDto> get(int bookId) {
        List<ReaderCardEntity> readerCardEntities = readerCardRepository.get(bookId);
        List<ReaderCardDto> readerCardDtos = new ArrayList<>();
        for (ReaderCardEntity readerCardEntity : readerCardEntities) {
            readerCardDtos.add(ReaderCardConverter.convertToReaderCardDto(readerCardEntity));
        }
        return readerCardDtos;
    }

    @Override
    public Date getNearestReturnDates(int bookId) {
        return readerCardRepository.getNearestReturnDates(bookId);
    }

    @Override
    public ReaderCardDto getReaderCard(int readerCardId) {
        ReaderCardEntity readerCardEntity = readerCardRepository.getReaderCard(readerCardId);
        ReaderCardDto readerCardDto = ReaderCardConverter.convertToReaderCardDto(readerCardEntity);
        if (readerCardEntity.getComment() == null) {
            readerCardDto.setComment("");
        }
        return readerCardDto;
    }

    @Override
    public void add(ReaderCardDto readerCardDto, Connection connection) throws SQLException {
        readerCardRepository.add(ReaderCardConverter.convertToReaderCardEntity(readerCardDto), connection);
    }

    @Override
    public void update(ReaderCardDto readerCardDto, Connection connection) throws SQLException {
        readerCardRepository.update(ReaderCardConverter.convertToReaderCardEntity(readerCardDto), connection);
    }

    @Override
    public void addOrUpdateReaderCard(ReaderCardDto readerCardDto, Connection connection) throws SQLException {
        ReaderDto readerDto = ReaderDto.builder()
                .email(readerCardDto.getReaderEmail())
                .name(readerCardDto.getReaderName())
                .build();
        readerCardDto.setReaderId(readerService.insertUpdateReaderGetId(readerDto, connection));
        if (readerCardDto.getId() == 0) {
            add(readerCardDto, connection);
        } else {
            update(readerCardDto, connection);
        }
    }

    @Override
    public List<ReaderCardDto> getExpiringReaderCards(int days) {
        List<ReaderCardEntity> readerCardEntities = readerCardRepository.getExpiringReaderCards(days);
        List<ReaderCardDto> readerCardDtoList = new ArrayList<>();
        for (ReaderCardEntity readerCardEntity : readerCardEntities) {
            readerCardDtoList.add(ReaderCardConverter.convertToReaderCardDto(readerCardEntity));
        }
        return readerCardDtoList;
    }

    @Override
    public int getBorrowBooksCount(int bookId) {
        return readerCardRepository.getActiveReaderCardsCount(bookId);
    }
}
