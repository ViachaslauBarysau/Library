package by.itechart.libmngmt.servlet.command.commands;

import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.servlet.command.LibraryCommand;
import lombok.Data;

import javax.servlet.ServletException;
import java.io.IOException;

@Data
public class AddEditBookCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static AddEditBookCommand instance = new AddEditBookCommand();

    public static AddEditBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }
}
