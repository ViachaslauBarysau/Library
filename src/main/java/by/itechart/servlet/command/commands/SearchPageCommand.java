package by.itechart.servlet.command.commands;

import by.itechart.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class SearchPageCommand extends LibraryCommand {

    private static SearchPageCommand instance = new SearchPageCommand();

    public static SearchPageCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }
}
