package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.repository.impl.ReaderRepositoryImpl;
import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.util.converter.ReaderConverter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderServiceImpl implements ReaderService {
    private ReaderConverter readerConverter = ReaderConverter.getInstance();
    private ReaderRepository readerRepository = ReaderRepositoryImpl.getInstance();
    private static volatile ReaderServiceImpl instance;

    public static synchronized ReaderServiceImpl getInstance() {
        ReaderServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public int insertUpdateReaderGetId(ReaderDto readerDto, Connection connection) {
        insertUpdateReader(readerDto);
        return getIdByEmail(readerDto.getEmail());
    }

    @Override
    public int getIdByEmail(String email) {
        return readerRepository.getId(email);
    }

    @Override
    public void insertUpdateReader(ReaderDto readerDto) {
        readerRepository.insertUpdate(readerConverter.convertReaderDtoToReaderEntity(readerDto));
    }

    @Override
    public List<String> getEmails(String pattern) {
        String refactoredPattern = pattern + "%";
        return readerRepository.getEmails(refactoredPattern);
    }

    @Override
    public String getNameByEmail(String email) {
        return readerRepository.getName(email);
    }
}
