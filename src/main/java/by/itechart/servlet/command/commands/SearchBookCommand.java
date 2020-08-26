package by.itechart.servlet.command.commands;

import by.itechart.servlet.command.LibraryCommand;
import lombok.Data;

import javax.servlet.ServletException;
import java.io.IOException;
@Data
public class SearchBookCommand extends LibraryCommand {

    private static SearchBookCommand instance = new SearchBookCommand();

    public static SearchBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }
}
