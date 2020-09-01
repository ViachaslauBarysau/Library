package by.itechart.libmngmt.service;

import by.itechart.libmngmt.dto.BookPageDto;

public interface BookManagementService {
    BookPageDto getBookPageDto(int bookId);
}
