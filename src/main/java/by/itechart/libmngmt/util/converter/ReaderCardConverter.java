package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;

public class ReaderCardConverter {
    public static ReaderCardDto convertToReaderCardDto(ReaderCardEntity readerCardEntity) {
        ReaderCardDto readerCardDto = ReaderCardDto.builder()
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
        return readerCardDto;
    }

    public static ReaderCardEntity convertToReaderCardEntity(ReaderCardDto readerCardDto) {
        ReaderCardEntity readerCardEntity = ReaderCardEntity.builder()
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
        return readerCardEntity;
    }

}
