package by.itechart.libmngmt.service.converter.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.service.converter.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderCardDtoEntityConverter implements Converter<ReaderCardDto, ReaderCardEntity> {
    private static volatile ReaderCardDtoEntityConverter instance;

    public static synchronized ReaderCardDtoEntityConverter getInstance() {
        ReaderCardDtoEntityConverter localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderCardDtoEntityConverter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderCardDtoEntityConverter();
                }
            }
        }
        return localInstance;
    }

    /**
     * Converts ReaderCardEntity to ReaderCardDto.
     *
     * @param readerCardEntity ReaderCardEntity object
     * @return ReaderCardDto object
     */
    @Override
    public ReaderCardDto convertToDto(final ReaderCardEntity readerCardEntity) {
        return ReaderCardDto.builder()
                .id(readerCardEntity.getId())
                .bookId(readerCardEntity.getBookId())
                .readerId(readerCardEntity.getReaderId())
                .bookTitle(readerCardEntity.getBookTitle())
                .readerEmail(readerCardEntity.getReaderEmail())
                .readerName(readerCardEntity.getReaderName())
                .borrowDate(readerCardEntity.getBorrowDate())
                .timePeriod(readerCardEntity.getTimePeriod())
                .dueDate(readerCardEntity.getDueDate())
                .returnDate(readerCardEntity.getReturnDate())
                .status(readerCardEntity.getStatus())
                .comment(readerCardEntity.getComment())
                .build();
    }

    /**
     * Converts ReaderCardDto to ReaderCardEntity.
     *
     * @param readerCardDto ReaderCardEntity object
     * @return ReaderCardDto object
     */
    @Override
    public ReaderCardEntity convertToEntity(final ReaderCardDto readerCardDto) {
        return ReaderCardEntity.builder()
                .id(readerCardDto.getId())
                .bookId(readerCardDto.getBookId())
                .readerId(readerCardDto.getReaderId())
                .bookTitle(readerCardDto.getBookTitle())
                .readerEmail(readerCardDto.getReaderEmail())
                .readerName(readerCardDto.getReaderName())
                .borrowDate(readerCardDto.getBorrowDate())
                .timePeriod(readerCardDto.getTimePeriod())
                .dueDate(readerCardDto.getDueDate())
                .returnDate(readerCardDto.getReturnDate())
                .status(readerCardDto.getStatus())
                .comment(readerCardDto.getComment())
                .build();
    }
}
