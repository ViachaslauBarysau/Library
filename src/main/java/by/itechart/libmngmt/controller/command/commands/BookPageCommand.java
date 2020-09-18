package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;

public class BookPageCommand extends LibraryCommand {
    private final BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private static BookPageCommand instance;

    public static BookPageCommand getInstance() {
        if(instance == null){
            instance = new BookPageCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        int bookId = 0;
        try {
            bookId = Integer.parseInt(request.getParameter("id"));
            if (bookId > 0) {
                BookPageDto bookPageDto = bookManagementService.getBookPageDto(bookId);
                request.setAttribute("bookpagedto", bookPageDto);
                forward("bookpage");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/lib-app?command=ADD_BOOK_PAGE");
        }
    }
}
