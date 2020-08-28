package by.itechart.servlet.command.commands;

import by.itechart.entity.Book;
import by.itechart.service.BookService;
import by.itechart.service.impl.BookServiceImpl;
import by.itechart.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class BookPageCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static BookPageCommand instance = new BookPageCommand();

    public static BookPageCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        Book book = bookService.getBook(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("book", book);
        forward("bookpage");
    }
}
