package by.itechart.libmngmt.service.impl;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.repository.impl.ReaderRepositoryImpl;
import by.itechart.libmngmt.service.ReaderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReaderServiceImpl implements ReaderService {

    ReaderRepository readerRepository = ReaderRepositoryImpl.getInstance();
    private static ReaderServiceImpl instance = new ReaderServiceImpl();

    public static ReaderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void add(ReaderDto readerDto) {

    }

//    @Override
//    public Map<Integer, ReaderDto> get(int bookId) {
//        Map<Integer, ReaderEntity> readerEntities = readerRepository.get(bookId);
//        Map<Integer, ReaderDto> readerDtos = new HashMap<>();
//        for (Map.Entry<Integer, ReaderEntity> entry: readerEntities.entrySet()) {
//            readerDtos.put(entry.getKey(), ReaderDto.builder()
//                                            .id(entry.getValue().getId())
//                                            .name(entry.getValue().getName())
//                                            .email(entry.getValue().getEmail())
//                                            .dateOfRegistration(entry.getValue().getDateOfRegistration())
//                                            .phoneNumber(entry.getValue().getPhoneNumber())
//                                            .build());
//        }
//        return readerDtos;
//    }

    @Override
    public List<String> getEmails(String pattern) {
        return readerRepository.getEmails(pattern + "%");
    }

    @Override
    public String getNameByEmail(String email) {
        return readerRepository.getName(email);
    }
}
