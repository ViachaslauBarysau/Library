package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class AddEditReaderCommand extends LibraryCommand {
    private static AddEditReaderCommand instance;

    public static AddEditReaderCommand getInstance() {
            if(instance == null){
                instance = new AddEditReaderCommand();
            }
            return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

    }

}
