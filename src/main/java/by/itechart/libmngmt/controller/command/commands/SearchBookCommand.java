package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SearchBookCommand extends LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(SearchBookCommand.class.getName());
    private static final int MIN_PAGE_NUMBER = 1;
    private static volatile SearchBookCommand instance;

    public static synchronized SearchBookCommand getInstance() {
        SearchBookCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (SearchBookCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SearchBookCommand();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void process() throws IOException {
        int pageNumber = getPageNumber();
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_PAGE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description + "&page=" + pageNumber);
    }

    private int getPageNumber() {
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong page number.", e);
        }
        return pageNumber;
    }
}
