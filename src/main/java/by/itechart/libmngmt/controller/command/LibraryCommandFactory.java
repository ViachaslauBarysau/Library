package by.itechart.libmngmt.controller.command;

import by.itechart.libmngmt.controller.command.commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages LibraryCommand objects getting.
 */
public class LibraryCommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(LibraryCommandFactory.class.getName());
    private static final Map<LibraryCommandType, LibraryCommand> commands = new HashMap<>();

    static {
        commands.put(LibraryCommandType.DELETE_BOOK, DeleteBookCommand.getInstance());
        commands.put(LibraryCommandType.ADD_BOOK, AddBookCommand.getInstance());
        commands.put(LibraryCommandType.EDIT_BOOK, EditBookCommand.getInstance());
        commands.put(LibraryCommandType.BOOK_PAGE, BookPageCommand.getInstance());
        commands.put(LibraryCommandType.GET_BOOK_LIST, GetBookListCommand.getInstance());
        commands.put(LibraryCommandType.SEARCH_EXECUTE, SearchExecuteCommand.getInstance());
        commands.put(LibraryCommandType.SEARCH_BOOK, SearchForwardCommand.getInstance());
        commands.put(LibraryCommandType.ADD_BOOK_PAGE, AddBookPageCommand.getInstance());
        commands.put(LibraryCommandType.DELETE_SEARCHED_BOOK, DeleteSearchedBookCommand.getInstance());
    }

    /**
     * Returns LibraryCommand object depending on request parameter "command".
     *
     * @return LibraryCommand object
     */
    public static LibraryCommand getCommand(final HttpServletRequest request) {
        try {
            return commands.get(LibraryCommandType.valueOf(request.getParameter("command")));
        } catch (Exception e) {
            LOGGER.debug("Wrong command.", e);
            return UnknownCommand.getInstance();
        }
    }
}
