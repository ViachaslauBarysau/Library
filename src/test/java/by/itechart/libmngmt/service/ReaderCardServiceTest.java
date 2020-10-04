package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import by.itechart.libmngmt.service.converter.impl.ReaderCardDtoEntityConverter;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReaderCardServiceTest {
    private static final int ID = 0;
    private Connection connection;

    @Mock
    ReaderCardRepository readerCardRepository;
    @Mock
    ReaderCardDtoEntityConverter readerCardConverter;
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
        when(readerCardConverter.convertToDto(readerCardEntity)).thenReturn(readerCardDto);
        // when
        List<ReaderCardDto> readerCardDtoList = readerCardService.getActiveReaderCards(ID);
        // then
        verify(readerCardRepository).getActiveReaderCards(ID);
        verify(readerCardConverter).convertToDto(readerCardEntity);
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
        when(readerCardConverter.convertToDto(readerCardEntity)).thenReturn(readerCardDto);
        // when
        List<ReaderCardDto> readerCardDtoList = readerCardService.getAllReaderCards(ID);
        verify(readerCardRepository).getAllReaderCards(ID);
        verify(readerCardConverter).convertToDto(readerCardEntity);
        // then
        assertEquals(expectedReaderCardDtoList, readerCardDtoList);
    }

    @Test
    public void testGetNearestReturnDates() {
        // given
        Date expectedDate = new Date(0);
        when(readerCardRepository.getNearestReturnDate(ID)).thenReturn(expectedDate);
        // when
        Date date = readerCardService.getNearestReturnDates(ID);
        // then
        verify(readerCardRepository).getNearestReturnDate(ID);
        assertEquals(expectedDate, date);
    }

    @Test
    public void testGetReaderCard() {
        // given
        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        ReaderCardDto expectedReaderCardDto = new ReaderCardDto();
        when(readerCardRepository.getReaderCard(ID)).thenReturn(readerCardEntity);
        when(readerCardConverter.convertToDto(readerCardEntity)).thenReturn(expectedReaderCardDto);
        // when
        ReaderCardDto readerCardDto = readerCardService.getReaderCard(ID);
        // then
        verify(readerCardRepository).getReaderCard(ID);
        verify(readerCardConverter).convertToDto(readerCardEntity);
        assertEquals(expectedReaderCardDto, readerCardDto);
    }

    @Test
    public void testAddOrUpdateReaderCard() throws SQLException {
        // given
        ReaderDto readerDto = new ReaderDto();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        when(readerService.insertUpdateReaderGetId(readerDto, connection)).thenReturn(ID);
        // when
        readerCardService.addOrUpdateReaderCard(readerCardDto, connection);
        // then
        verify(readerService).insertUpdateReaderGetId(readerDto, connection);
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
        when(readerCardConverter.convertToDto(readerCardEntity)).thenReturn(readerCardDto);
        // when
        List<ReaderCardDto> readerCardDtoList = readerCardService.getExpiringReaderCards(anyInt());
        // then
        verify(readerCardRepository).getExpiringReaderCards(ID);
        verify(readerCardConverter).convertToDto(readerCardEntity);
        assertEquals(expectedReaderCardDtoList, readerCardDtoList);
    }

    @Test(expected = SQLException.class)
    public void testAddOrUpdateReaderCardThrowsException() throws SQLException {
        // given
        ReaderDto readerDto = new ReaderDto();
        ReaderCardDto readerCardDto = new ReaderCardDto();
        when(readerService.insertUpdateReaderGetId(readerDto, connection)).thenThrow(new SQLException());
        // when
        readerCardService.addOrUpdateReaderCard(readerCardDto, connection);
        // then
        verify(readerService).insertUpdateReaderGetId(readerDto, connection);
    }
}
