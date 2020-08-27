package by.itechart.servlet.command;

import by.itechart.servlet.command.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LibraryCommandFactory {

    private static final Map<LibraryCommandType, LibraryCommand> commands = new HashMap<>();

    static  {
        commands.put(LibraryCommandType.DELETE_BOOK, DeleteBookCommand.getInstance());
        commands.put(LibraryCommandType.ADD_EDIT_BOOK, AddEditBookCommand.getInstance());
        commands.put(LibraryCommandType.ADD_EDIT_READER, AddEditReaderCommand.getInstance());
        commands.put(LibraryCommandType.BOOK_PAGE, AddEditBookCommand.getInstance());
        commands.put(LibraryCommandType.GET_BOOK_LIST, GetBookListCommand.getInstance());
        commands.put(LibraryCommandType.SEARCH_PAGE, SearchPageCommand.getInstance());
        commands.put(LibraryCommandType.SEARCH_BOOK, SearchBookCommand.getInstance());
    }

    public static LibraryCommand getCommand(HttpServletRequest request) {
        try {
            return commands.get(LibraryCommandType.valueOf(request.getParameter("command")));
        } catch (Exception e) {
            return UnknownCommand.getInstance();
        }

    }

}
