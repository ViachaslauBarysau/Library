package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.service.impl.ReaderServiceImpl;
import by.itechart.libmngmt.service.converter.impl.ReaderDtoEntityConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceTest {
    private Connection connection;
    @Mock
    ReaderRepository readerRepository;
    @Mock
    ReaderDtoEntityConverter readerConverter;
    @InjectMocks
    ReaderServiceImpl readerService;

    @Test
    public void testGetNameByEmail() {
        //given
        String email = "someEmail";
        String expectedName = "someName";
        when(readerRepository.getName(email)).thenReturn(expectedName);
        //when
        String name = readerService.getNameByEmail(email);
        //then
        verify(readerRepository).getName(email);
        assertEquals(expectedName, name);
    }

    @Test
    public void testGetEmails() {
        //given
        String pattern = "somePattern";
        List<String> expectedEmailList = new ArrayList<>();
        when(readerRepository.getEmails(anyString())).thenReturn(expectedEmailList);
        //when
        List<String> emailList = readerService.getEmails(pattern);
        //then
        verify(readerRepository).getEmails(anyString());
        assertEquals(emailList, emailList);
    }

    @Test
    public void TestInsertUpdateReaderGetId() throws SQLException {
        //given
        int expectedId = 0;
        String email = "someEmail";
        ReaderDto readerDto = new ReaderDto();
        readerDto.setEmail(email);
        ReaderEntity readerEntity = new ReaderEntity();
        when(readerRepository.getId(readerDto.getEmail(), connection)).thenReturn(expectedId);
        when(readerConverter.convertToEntity(readerDto)).thenReturn(readerEntity);
        //when
        int readerId = readerService.insertUpdateReaderGetId(readerDto, connection);
        //then
        verify(readerRepository).insertUpdate(readerEntity, connection);
        verify(readerConverter).convertToEntity(readerDto);
        verify(readerRepository).getId(email, connection);
        assertEquals(expectedId, readerId);
    }

    @Test(expected = SQLException.class)
    public void TestInsertUpdateReaderGetIdThrowsException() throws SQLException {
        //given
        int expectedId = 0;
        String email = "someEmail";
        ReaderDto readerDto = new ReaderDto();
        readerDto.setEmail(email);
        ReaderEntity readerEntity = new ReaderEntity();
        when(readerRepository.getId(readerDto.getEmail(), connection)).thenThrow(new SQLException());
        when(readerConverter.convertToEntity(readerDto)).thenReturn(readerEntity);
        //when
        int readerId = readerService.insertUpdateReaderGetId(readerDto, connection);
        //then
        verify(readerRepository).insertUpdate(readerEntity, connection);
        verify(readerConverter).convertToEntity(readerDto);
        verify(readerRepository).getId(email, connection);
        assertEquals(expectedId, readerId);
    }
}




