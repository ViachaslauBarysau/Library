package by.itechart.servlet.command;

import by.itechart.servlet.command.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LibraryCommandFactory {

    private static final Map<LibraryCommandType, LibraryCommand> commands = new HashMap<>();

    static  {
        commands.put(LibraryCommandType.DELETEBOOK, DeleteBookCommand.getInstance());
        commands.put(LibraryCommandType.ADDEDITBOOK, AddEditBookCommand.getInstance());
        commands.put(LibraryCommandType.ADDEDITREADER, AddEditReaderCommand.getInstance());
        commands.put(LibraryCommandType.BOOKPAGE, AddEditBookCommand.getInstance());
        commands.put(LibraryCommandType.GETBOOKLIST, GetBookListCommand.getInstance());
        commands.put(LibraryCommandType.SEARCHPAGE, SearchPageCommand.getInstance());
        commands.put(LibraryCommandType.SEARCHBOOK, SearchBookCommand.getInstance());
    }

    public static LibraryCommand getCommand(HttpServletRequest request) {
        try {
            return commands.get(LibraryCommandType.valueOf(request.getParameter("command")));
        } catch (Exception e) {
            return UnknownCommand.getInstance();
        }

    }

}
