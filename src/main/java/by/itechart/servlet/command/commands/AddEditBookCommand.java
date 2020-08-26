package by.itechart.servlet.command.commands;

import by.itechart.servlet.command.LibraryCommand;
import lombok.Data;

import javax.servlet.ServletException;
import java.io.IOException;

@Data
public class AddEditBookCommand extends LibraryCommand {

    private static AddEditBookCommand instance = new AddEditBookCommand();

    public static AddEditBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }
}
