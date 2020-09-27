package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteSearchedBookCommand extends LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(DeleteSearchedBookCommand.class.getName());
    private static final int MIN_PAGE_NUMBER = 1;
    private BookService bookService = BookServiceImpl.getInstance();
    private static volatile DeleteSearchedBookCommand instance;

    public static synchronized DeleteSearchedBookCommand getInstance() {
        DeleteSearchedBookCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (DeleteSearchedBookCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DeleteSearchedBookCommand();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void process() throws IOException {
        deleteBooks(request);
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        int pageNumber = setPageNumber();
        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_PAGE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description + "&page=" + pageNumber);
    }

    private void deleteBooks(HttpServletRequest request) {
        try {
            List<Integer> booksIdsForDeleting = Arrays.asList(
                    request.getParameterValues("bookid")).stream()
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());
            bookService.delete(booksIdsForDeleting);
        } catch (Exception e) {
            LOGGER.debug("Getting book's list for deleting error.", e);
        }
    }

    private int setPageNumber() {
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong book page.", e);
        }
        return pageNumber;
    }
}
