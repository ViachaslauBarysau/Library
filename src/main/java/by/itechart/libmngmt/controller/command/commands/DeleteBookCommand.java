package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteBookCommand extends LibraryCommand {
    private final static Logger LOGGER = LogManager.getLogger(DeleteBookCommand.class.getName());
    private final BookService bookService = BookServiceImpl.getInstance();
    private final int MIN_PAGE_NUMBER = 1;
    private static DeleteBookCommand instance;

    public static synchronized DeleteBookCommand getInstance() {
        if(instance == null){
            instance = new DeleteBookCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        deleteBooks(request);
        int pageNumber = setPageNumber(request);
        int countOfPages = bookService.getPageCount();
        pageNumber = Math.min(countOfPages, pageNumber);
        response.sendRedirect(request.getContextPath() + "lib-app?command=GET_BOOK_LIST&page=" + pageNumber);
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

    private int setPageNumber(HttpServletRequest request) {
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong book page.", e);
        }
        return pageNumber;
    }
}
