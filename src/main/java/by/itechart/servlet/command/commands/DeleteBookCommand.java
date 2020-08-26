package by.itechart.servlet.command.commands;

import by.itechart.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class DeleteBookCommand extends LibraryCommand {

    private static DeleteBookCommand instance = new DeleteBookCommand();

    public static DeleteBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }
}
