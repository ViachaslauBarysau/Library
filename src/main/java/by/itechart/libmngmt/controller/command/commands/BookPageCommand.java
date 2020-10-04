package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Processes requests of opening book page.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookPageCommand extends LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(BookPageCommand.class.getName());
    public static final int MIN_BOOK_ID = 0;
    private BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private static volatile BookPageCommand instance;

    public static synchronized BookPageCommand getInstance() {
        BookPageCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (BookPageCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BookPageCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Forwards to the book page with necessary attributes or
     * redirects to error page if request parameters are incorrect.
     *
     * @throws IOException in case of servlet failure
     */
    @Override
    public void process() throws IOException {
        try {
            int bookId = Integer.parseInt(request.getParameter("id"));
            boolean isBookIdExisting = bookId > MIN_BOOK_ID;
            if (isBookIdExisting) {
                BookPageDto bookPageDto = bookManagementService.getBookPageDto(bookId);
                request.setAttribute("bookpagedto", bookPageDto);
                forward("bookpage");
            } else {
                response.sendRedirect(request.getContextPath() + "/lib-app?command=UNKNOWN");
            }
        } catch (Exception e) {
            LOGGER.debug("Wrong book page.", e);
            response.sendRedirect(request.getContextPath() + "/lib-app?command=UNKNOWN");
        }
    }
}
