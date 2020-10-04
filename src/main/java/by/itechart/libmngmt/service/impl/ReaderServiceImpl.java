package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.repository.impl.ReaderRepositoryImpl;
import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.service.converter.Converter;
import by.itechart.libmngmt.service.converter.ConverterFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides methods for operations with readers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderServiceImpl implements ReaderService {
    private static final int READER_DTO_ENTITY_CONVERTER_TYPE = 3;
    private Converter<ReaderDto, ReaderEntity> readerDtoEntityConverter
            = ConverterFactory.getInstance().createConverter(READER_DTO_ENTITY_CONVERTER_TYPE);
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

    /**
     * Adds new or updates existing reader and returns his ID.
     *
     * @param readerDto  ReaderDto object
     * @param connection current connection
     * @return reader ID
     * @throws SQLException in case of SQL failure
     */
    @Override
    public int insertUpdateReaderGetId(final ReaderDto readerDto, final Connection connection) throws SQLException {
        insertUpdateReader(readerDto, connection);
        return getIdByEmail(readerDto.getEmail(), connection);
    }

    /**
     * Returns list of emails searched by pattern. It refactor pattern before invoking
     * repository, so no need to do any changes before invoking this method.
     *
     * @param pattern email pattern
     * @return list of emails
     */
    @Override
    public List<String> getEmails(final String pattern) {
        String refactoredPattern = pattern + "%";
        return readerRepository.getEmails(refactoredPattern);
    }

    /**
     * Returns reader's name searched by reader's email.
     *
     * @param email reader email
     * @return reader name
     */
    @Override
    public String getNameByEmail(final String email) {
        return readerRepository.getName(email);
    }

    private int getIdByEmail(final String email, final Connection connection) throws SQLException {
        return readerRepository.getId(email, connection);
    }

    private void insertUpdateReader(final ReaderDto readerDto, final Connection connection) throws SQLException {
        readerRepository.insertUpdate(readerDtoEntityConverter.convertToEntity(readerDto), connection);
    }
}
