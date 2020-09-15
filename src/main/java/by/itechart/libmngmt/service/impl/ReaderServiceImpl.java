package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.repository.impl.ReaderRepositoryImpl;
import by.itechart.libmngmt.service.ReaderService;

import java.util.List;

public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository = ReaderRepositoryImpl.getInstance();
    private static ReaderServiceImpl instance;

    public static ReaderServiceImpl getInstance() {
        if(instance == null){
            instance = new ReaderServiceImpl();
        }
        return instance;
    }

    @Override
    public int insertUpdateReaderGetId(ReaderDto readerDto) {
        insertUpdateReader(readerDto);
        return getIdByEmail(readerDto.getEmail());
    }

    @Override
    public int getIdByEmail(String email) {
        return readerRepository.getId(email);
    }

    @Override
    public void insertUpdateReader(ReaderDto readerDto) {
        ReaderEntity readerEntity = ReaderEntity.builder()
                .email(readerDto.getEmail())
                .name(readerDto.getName())
                .build();
        readerRepository.insertUpdate(readerEntity);
    }

    @Override
    public List<String> getEmails(String pattern) {
        return readerRepository.getEmails(pattern + "%");
    }

    @Override
    public String getNameByEmail(String email) {
        return readerRepository.getName(email);
    }
}
