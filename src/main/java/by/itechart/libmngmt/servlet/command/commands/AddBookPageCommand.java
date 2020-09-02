package by.itechart.libmngmt.servlet.command.commands;

import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class AddBookPageCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static AddBookPageCommand instance = new AddBookPageCommand();

    public static AddBookPageCommand getInstance() {
        return instance;
    }


    @Override
    public void process() throws ServletException, IOException {
        request.setAttribute("command", "EDIT_BOOK");

        bookService.addBook(bookService.getBook(1));
        forward("bookpage");
    }
}
