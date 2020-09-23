package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;

public class ReaderConverter {
    public static ReaderEntity convertReaderDtoToReaderEntity(ReaderDto readerDto) {
        ReaderEntity readerEntity = ReaderEntity.builder()
                .id(readerDto.getId())
                .email(readerDto.getEmail())
                .name(readerDto.getName())
                .build();
        return readerEntity;
    }
}
