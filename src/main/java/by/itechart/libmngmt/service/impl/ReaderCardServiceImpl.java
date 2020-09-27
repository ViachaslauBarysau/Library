package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.util.converter.ReaderCardConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderCardServiceImpl implements ReaderCardService {
    private ReaderCardConverter readerCardConverter = ReaderCardConverter.getInstance();
    private ReaderService readerService = ReaderServiceImpl.getInstance();
    private ReaderCardRepository readerCardRepository = ReaderCardRepositoryImpl.getInstance();
    private static volatile ReaderCardServiceImpl instance;

    public static synchronized ReaderCardServiceImpl getInstance() {
        ReaderCardServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderCardServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderCardServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<ReaderCardDto> getActiveReaderCards(int bookId) {
        List<ReaderCardEntity> readerCardEntities = readerCardRepository.getActiveReaderCards(bookId);
        List<ReaderCardDto> readerCardDtos = new ArrayList<>();
        for (ReaderCardEntity readerCardEntity : readerCardEntities) {
            readerCardDtos.add(readerCardConverter.convertToReaderCardDto(readerCardEntity));
        }
        return readerCardDtos;
    }

    @Override
    public List<ReaderCardDto> getAllReaderCards(int bookId) {
        List<ReaderCardEntity> readerCardEntities = readerCardRepository.getAllReaderCards(bookId);
        List<ReaderCardDto> readerCardDtos = new ArrayList<>();
        for (ReaderCardEntity readerCardEntity : readerCardEntities) {
            readerCardDtos.add(readerCardConverter.convertToReaderCardDto(readerCardEntity));
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
        ReaderCardDto readerCardDto = readerCardConverter.convertToReaderCardDto(readerCardEntity);
        if (readerCardEntity.getComment() == null) {
            readerCardDto.setComment("");
        }
        return readerCardDto;
    }

    @Override
    public void add(ReaderCardDto readerCardDto, Connection connection) throws SQLException {
        readerCardRepository.add(readerCardConverter.convertToReaderCardEntity(readerCardDto), connection);
    }

    @Override
    public void update(ReaderCardDto readerCardDto, Connection connection) throws SQLException {
        readerCardRepository.update(readerCardConverter.convertToReaderCardEntity(readerCardDto), connection);
    }

    @Override
    public void addOrUpdateReaderCard(ReaderCardDto readerCardDto, Connection connection) throws SQLException {
        ReaderDto readerDto = ReaderDto.builder()
                .email(readerCardDto.getReaderEmail())
                .name(readerCardDto.getReaderName())
                .build();
        readerCardDto.setReaderId(readerService.insertUpdateReaderGetId(readerDto, connection));
        Boolean readerCardIsNew = new Boolean(readerCardDto.getId() == 0);
        if (readerCardIsNew) {
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
            readerCardDtoList.add(readerCardConverter.convertToReaderCardDto(readerCardEntity));
        }
        return readerCardDtoList;
    }
}
