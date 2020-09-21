package by.itechart.libmngmt.controller.command;

import by.itechart.libmngmt.controller.command.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class LibraryCommandFactory {

    private static final Map<LibraryCommandType, LibraryCommand> commands = new HashMap<>();

    static  {
        commands.put(LibraryCommandType.DELETE_BOOK, DeleteBookCommand.getInstance());
        commands.put(LibraryCommandType.ADD_EDIT_BOOK, AddEditBookCommand.getInstance());
        commands.put(LibraryCommandType.BOOK_PAGE, BookPageCommand.getInstance());
        commands.put(LibraryCommandType.GET_BOOK_LIST, GetBookListCommand.getInstance());
        commands.put(LibraryCommandType.SEARCH_PAGE, SearchPageCommand.getInstance());
        commands.put(LibraryCommandType.SEARCH_BOOK, SearchBookCommand.getInstance());
        commands.put(LibraryCommandType.ADD_BOOK_PAGE, AddBookPageCommand.getInstance());
        commands.put(LibraryCommandType.DELETE_SEARCHED_BOOK, DeleteSearchedBookCommand.getInstance());
    }

    public static LibraryCommand getCommand(HttpServletRequest request) {
        try {
            return commands.get(LibraryCommandType.valueOf(request.getParameter("command")));
        } catch (Exception e) {
            return UnknownCommand.getInstance();
        }

    }

}
