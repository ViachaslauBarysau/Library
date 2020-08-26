package by.itechart.servlet.command.commands;

import by.itechart.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class UnknownCommand extends LibraryCommand {

    private static UnknownCommand instance = new UnknownCommand();

    public static UnknownCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }
}
