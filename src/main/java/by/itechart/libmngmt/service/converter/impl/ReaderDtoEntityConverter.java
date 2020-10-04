package by.itechart.libmngmt.service.converter.impl;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.service.converter.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderDtoEntityConverter implements Converter<ReaderDto, ReaderEntity> {
    private static volatile ReaderDtoEntityConverter instance;

    public static synchronized ReaderDtoEntityConverter getInstance() {
        ReaderDtoEntityConverter localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderDtoEntityConverter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderDtoEntityConverter();
                }
            }
        }
        return localInstance;
    }

    @Override
    public ReaderDto convertToDto(final ReaderEntity readerEntity) {
        //No need to override because of uselessness.
        return null;
    }

    /**
     * Converts ReaderDto to ReaderEntity.
     *
     * @param readerDto ReaderDto object
     * @return ReaderEntity object
     */
    @Override
    public ReaderEntity convertToEntity(final ReaderDto readerDto) {
        return ReaderEntity.builder()
                .id(readerDto.getId())
                .email(readerDto.getEmail())
                .name(readerDto.getName())
                .build();
    }
}
