package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class UnknownCommand extends LibraryCommand {
    private static volatile UnknownCommand instance;

    public static synchronized UnknownCommand getInstance() {
        UnknownCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (UnknownCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UnknownCommand();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void process() throws ServletException, IOException {
        forward("unknownpage");
    }
}
