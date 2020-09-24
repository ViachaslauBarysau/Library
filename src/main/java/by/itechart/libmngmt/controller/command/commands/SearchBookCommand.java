package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

@Data
public class SearchBookCommand extends LibraryCommand {
    private final static Logger LOGGER = LogManager.getLogger(SearchBookCommand.class.getName());
    private final BookService bookService = BookServiceImpl.getInstance();
    private final int MIN_PAGE_NUMBER = 1;
    private static SearchBookCommand instance;

    public static synchronized SearchBookCommand getInstance() {
        if(instance == null){
            instance = new SearchBookCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong page number.", e);
        }
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_PAGE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description +   "&page=" + pageNumber);
    }

}
