package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;

public class ReaderConverter {
    private static volatile ReaderConverter instance;

    public static synchronized ReaderConverter getInstance() {
        ReaderConverter localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderConverter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderConverter();
                }
            }
        }
        return localInstance;
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
