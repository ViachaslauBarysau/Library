package by.itechart.servlet.command.commands;

import by.itechart.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class AddEditReaderCommand extends LibraryCommand {
    private static AddEditReaderCommand instance = new AddEditReaderCommand();

    public static AddEditReaderCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }

}
