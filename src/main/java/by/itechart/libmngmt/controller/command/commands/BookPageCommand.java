package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.controller.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class BookPageCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private static BookPageCommand instance = new BookPageCommand();

    public static BookPageCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        BookPageDto bookPageDto = bookManagementService.getBookPageDto(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("bookpagedto", bookPageDto);
        forward("bookpage");
    }
}
