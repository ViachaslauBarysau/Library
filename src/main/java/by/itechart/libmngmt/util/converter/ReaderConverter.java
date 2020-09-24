package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;

public class ReaderConverter {
    private static ReaderConverter instance;

    public static synchronized ReaderConverter getInstance() {
        if(instance == null){
            instance = new ReaderConverter();
        }
        return instance;
    }

    public ReaderEntity convertReaderDtoToReaderEntity(ReaderDto readerDto) {
        ReaderEntity readerEntity = ReaderEntity.builder()
                .id(readerDto.getId())
                .email(readerDto.getEmail())
                .name(readerDto.getName())
                .build();
        return readerEntity;
    }
}
