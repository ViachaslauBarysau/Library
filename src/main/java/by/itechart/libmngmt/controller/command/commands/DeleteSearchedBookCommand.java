package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteSearchedBookCommand extends LibraryCommand {
    private final static Logger logger = LogManager.getLogger(DeleteSearchedBookCommand.class.getName());
    private final BookService bookService = BookServiceImpl.getInstance();
    private final int MIN_PAGE_NUMBER = 1;
    private static DeleteSearchedBookCommand instance;

    public static DeleteSearchedBookCommand getInstance() {
        if(instance == null){
            instance = new DeleteSearchedBookCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

        try {
            List<Integer> booksIdsForDeleting = Arrays.asList(request.getParameterValues("bookid")).stream()
                    .mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            bookService.delete(booksIdsForDeleting);
        } catch (Exception e) {
            logger.debug("Getting book's list for deleting error!", e);
        }

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            logger.debug("Wrong book page!", e);
        }
        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_PAGE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description +   "&page=" + pageNumber);
    }
}
