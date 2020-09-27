package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import by.itechart.libmngmt.util.ConnectionPool;
import by.itechart.libmngmt.util.converter.ReaderCardConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReaderCardServiceTest {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final int ID = 0;
    private static final Date DATE = new Date(0);
    final private Connection CONNECTION = connectionPool.getConnection();

    @Mock
    ReaderCardRepository readerCardRepository;
    @Mock
    ReaderCardConverter readerCardConverter;
    @Mock
    ReaderService readerService;
    @InjectMocks
    ReaderCardServiceImpl readerCardService;

    @Test
    public void testGetActiveReaderCards() {
        // given
        List<ReaderCardEntity> readerCardEntities = new ArrayList<>();
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        readerCardEntities.add(readerCardEntity);
        List<ReaderCardDto> expectedReaderCardDtoList = new ArrayList<>();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        expectedReaderCardDtoList.add(readerCardDto);
        when(readerCardRepository.getActiveReaderCards(ID)).thenReturn(readerCardEntities);
        when(readerCardConverter.convertToReaderCardDto(readerCardEntity)).thenReturn(readerCardDto);
        // when
        List<ReaderCardDto> readerCardDtoList = readerCardService.getActiveReaderCards(ID);
        // then
        verify(readerCardRepository).getActiveReaderCards(ID);
        verify(readerCardConverter).convertToReaderCardDto(readerCardEntity);
        assertEquals(expectedReaderCardDtoList, readerCardDtoList);
    }

    @Test
    public void testGetAllReaderCards() {
        // given
        List<ReaderCardEntity> readerCardEntities = new ArrayList<>();
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        readerCardEntities.add(readerCardEntity);
        List<ReaderCardDto> expectedReaderCardDtoList = new ArrayList<>();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        expectedReaderCardDtoList.add(readerCardDto);
        when(readerCardRepository.getAllReaderCards(ID)).thenReturn(readerCardEntities);
        when(readerCardConverter.convertToReaderCardDto(readerCardEntity)).thenReturn(readerCardDto);
        // when
        List<ReaderCardDto> readerCardDtoList = readerCardService.getAllReaderCards(ID);
        verify(readerCardRepository).getAllReaderCards(ID);
        verify(readerCardConverter).convertToReaderCardDto(readerCardEntity);
        // then
        assertEquals(expectedReaderCardDtoList, readerCardDtoList);
    }

    @Test
    public void testGetNearestReturnDates() {
        // given
        when(readerCardRepository.getNearestReturnDates(ID)).thenReturn(DATE);
        // when
        Date date = readerCardService.getNearestReturnDates(ID);
        // then
        verify(readerCardRepository).getNearestReturnDates(ID);
        assertEquals(DATE, date);
    }

    @Test
    public void testGetReaderCard() {
        // given
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        ReaderCardDto expectedReaderCardDto = new ReaderCardDto();
        when(readerCardRepository.getReaderCard(ID)).thenReturn(readerCardEntity);
        when(readerCardConverter.convertToReaderCardDto(readerCardEntity)).thenReturn(expectedReaderCardDto);
        // when
        ReaderCardDto readerCardDto = readerCardService.getReaderCard(ID);
        // then
        verify(readerCardRepository).getReaderCard(ID);
        verify(readerCardConverter).convertToReaderCardDto(readerCardEntity);
        assertEquals(expectedReaderCardDto, readerCardDto);
    }

    @Test
    public void testAdd() throws SQLException {
        // given
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        when(readerCardConverter.convertToReaderCardEntity(readerCardDto)).thenReturn(readerCardEntity);
        // when
        readerCardService.add(readerCardDto, CONNECTION);
        // then
        verify(readerCardRepository).add(readerCardEntity, CONNECTION);
        verify(readerCardConverter).convertToReaderCardEntity(readerCardDto);
    }

    @Test
    public void testUpdate() throws SQLException {
        // given
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        when(readerCardConverter.convertToReaderCardEntity(readerCardDto)).thenReturn(readerCardEntity);
        // when
        readerCardService.update(readerCardDto, CONNECTION);
        // then
        verify(readerCardRepository).update(readerCardEntity, CONNECTION);
        verify(readerCardConverter).convertToReaderCardEntity(readerCardDto);
    }

    @Test
    public void testAddOrUpdateReaderCard() throws SQLException {
        // given
        ReaderDto readerDto = new ReaderDto();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        when(readerService.insertUpdateReaderGetId(readerDto, CONNECTION)).thenReturn(ID);
        // when
        readerCardService.addOrUpdateReaderCard(readerCardDto, CONNECTION);
        // then
        verify(readerService).insertUpdateReaderGetId(readerDto, CONNECTION);
    }
    @Test
    public void testGetExpiringReaderCards() {
        // given
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        List<ReaderCardEntity> readerCardEntities = new ArrayList<>();
        readerCardEntities.add(new ReaderCardEntity());
        List<ReaderCardDto> expectedReaderCardDtoList = new ArrayList<>();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        expectedReaderCardDtoList.add(readerCardDto);
        when(readerCardRepository.getExpiringReaderCards(anyInt())).thenReturn(readerCardEntities);
        when(readerCardConverter.convertToReaderCardDto(readerCardEntity)).thenReturn(readerCardDto);
        // when
        List<ReaderCardDto> readerCardDtoList = readerCardService.getExpiringReaderCards(anyInt());
        // then
        verify(readerCardRepository).getExpiringReaderCards(ID);
        verify(readerCardConverter).convertToReaderCardDto(readerCardEntity);
        assertEquals(expectedReaderCardDtoList, readerCardDtoList);
    }
}
