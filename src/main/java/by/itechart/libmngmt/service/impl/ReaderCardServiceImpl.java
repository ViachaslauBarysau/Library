package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.service.converter.Converter;
import by.itechart.libmngmt.service.converter.ConverterFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides methods for operations with reader cards.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderCardServiceImpl implements ReaderCardService {
    private static final String EMPTY_STRING = "";
    private static final int READER_CARD_DTO_ENTITY_CONVERTER_TYPE = 2;
    private Converter<ReaderCardDto, ReaderCardEntity> readerCardDtoEntityConverter
            = ConverterFactory.getInstance().createConverter(READER_CARD_DTO_ENTITY_CONVERTER_TYPE);
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

    /**
     * Adds new or update existing reader card. It checks reader ID and
     * invokes add(ID=0) or update methods.
     *
     * @param readerCardDto ReaderCardDto object
     * @param connection    current connection
     * @throws SQLException in case of SQL failure
     */
    @Override
    public void addOrUpdateReaderCard(final ReaderCardDto readerCardDto, final Connection connection)
            throws SQLException {
        ReaderDto readerDto = ReaderDto.builder()
                .email(readerCardDto.getReaderEmail())
                .name(readerCardDto.getReaderName())
                .build();
        readerCardDto.setReaderId(readerService.insertUpdateReaderGetId(readerDto, connection));
        boolean isReaderCardNew = readerCardDto.getId() == 0;
        if (isReaderCardNew) {
            add(readerCardDto, connection);
        } else {
            update(readerCardDto, connection);
        }
    }

    /**
     * Returns list of active reader cards of book(reader cards without return date).
     *
     * @param bookId book ID
     * @return list of ReaderCardDto
     */
    @Override
    public List<ReaderCardDto> getActiveReaderCards(final int bookId) {
        return readerCardRepository.getActiveReaderCards(bookId).stream()
                .map(readerCardEntity -> readerCardDtoEntityConverter.convertToDto(readerCardEntity))
                .collect(Collectors.toList());
    }

    /**
     * Returns list of reader cards of specific book.
     *
     * @param bookId book ID
     * @return list of ReaderCardDto
     */
    @Override
    public List<ReaderCardDto> getAllReaderCards(final int bookId) {
        return readerCardRepository.getAllReaderCards(bookId).stream()
                .map(readerCardEntity -> readerCardDtoEntityConverter.convertToDto(readerCardEntity))
                .collect(Collectors.toList());
    }

    /**
     * Returns nearest available date when book will be available(if its unavailable).
     *
     * @param bookId book ID
     * @return Date object
     */
    @Override
    public Date getNearestReturnDates(final int bookId) {
        return readerCardRepository.getNearestReturnDate(bookId);
    }

    /**
     * Returns reader card searched by its ID.
     *
     * @param readerCardId reader card ID
     * @return ReaderCardDto object
     */
    @Override
    public ReaderCardDto getReaderCard(final int readerCardId) {
        ReaderCardEntity readerCardEntity = readerCardRepository.getReaderCard(readerCardId);
        ReaderCardDto readerCardDto = readerCardDtoEntityConverter.convertToDto(readerCardEntity);
        if (readerCardEntity.getComment() == null) {
            readerCardDto.setComment(EMPTY_STRING);
        }
        return readerCardDto;
    }

    /**
     * Returns list of reader cards where due date is almost expire(or already expired).
     *
     * @param days count of days before/after due date
     * @return list of ReaderCardDto
     */
    @Override
    public List<ReaderCardDto> getExpiringReaderCards(int days) {
        return readerCardRepository.getExpiringReaderCards(days).stream()
                .map(readerCardEntity -> readerCardDtoEntityConverter.convertToDto(readerCardEntity))
                .collect(Collectors.toList());
    }

    private void add(final ReaderCardDto readerCardDto, final Connection connection) throws SQLException {
        readerCardRepository.add(readerCardDtoEntityConverter.convertToEntity(readerCardDto), connection);
    }

    private void update(final ReaderCardDto readerCardDto, final Connection connection) throws SQLException {
        readerCardRepository.update(readerCardDtoEntityConverter.convertToEntity(readerCardDto), connection);
    }
}
