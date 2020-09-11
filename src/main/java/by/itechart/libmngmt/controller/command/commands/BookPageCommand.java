package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.repository.ReaderCardRepository;
import by.itechart.libmngmt.repository.impl.ReaderCardRepositoryImpl;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class BookPageCommand extends LibraryCommand {
    private ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    private BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private static BookPageCommand instance = new BookPageCommand();

    public static BookPageCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        BookPageDto bookPageDto = bookManagementService.getBookPageDto(bookId);
        request.setAttribute("bookpagedto", bookPageDto);
        forward("bookpage");
    }
}
