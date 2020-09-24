package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class UnknownCommand extends LibraryCommand {
    private static UnknownCommand instance;

    public static synchronized UnknownCommand getInstance() {
        if(instance == null){
            instance = new UnknownCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }
}
