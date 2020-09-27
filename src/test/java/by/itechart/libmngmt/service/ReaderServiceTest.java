package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.repository.ReaderRepository;
import by.itechart.libmngmt.service.impl.ReaderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceTest {
    final private int ID = 0;
    final private String EMAIL = "someEmail";
    final private String NAME = "someName";
    final private String PATTERN = "somePattern";
    final private String REFACTORED_PATTERN = "somePattern%";
    final private ReaderDto READER_DTO = new ReaderDto();
    final private ReaderEntity READER_ENTITY = new ReaderEntity();
    final List<String> EMAIL_LIST = new ArrayList<>();

    @Mock
    ReaderRepository readerRepository;
    @InjectMocks
    ReaderServiceImpl readerService;

    @Test
    public void testGetIdByEmail() {
        when(readerRepository.getId(EMAIL)).thenReturn(ID);
        int readerId = readerService.getIdByEmail(EMAIL);
        verify(readerRepository).getId(EMAIL);
        assertEquals(ID, readerId);
    }

    @Test
    public void testGetNameByEmail() {
        when(readerRepository.getName(EMAIL)).thenReturn(NAME);
        String name = readerService.getNameByEmail(EMAIL);
        verify(readerRepository).getName(EMAIL);
        assertEquals(NAME, name);
    }

    @Test
    public void testGetEmails() {
        when(readerRepository.getEmails(REFACTORED_PATTERN)).thenReturn(EMAIL_LIST);
        List<String> emailList = readerService.getEmails(PATTERN);
        verify(readerRepository).getEmails(REFACTORED_PATTERN);
        assertEquals(EMAIL_LIST, emailList);
    }

    @Test
    public void testInsertUpdateReader() {
        readerService.insertUpdateReader(READER_DTO);
        verify(readerRepository).insertUpdate(READER_ENTITY);
    }
}




