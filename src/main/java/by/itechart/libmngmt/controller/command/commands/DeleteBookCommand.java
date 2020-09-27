package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteBookCommand extends LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(DeleteBookCommand.class.getName());
    private static final int MIN_PAGE_NUMBER = 1;
    private BookService bookService = BookServiceImpl.getInstance();
    private static volatile DeleteBookCommand instance;

    public static synchronized DeleteBookCommand getInstance() {
        DeleteBookCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (DeleteBookCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DeleteBookCommand();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void process() throws IOException {
        deleteBooks();
        int countOfPages = bookService.getPageCount();
        int pageNumber = setPageNumber(countOfPages);
        String hideUnavailableCheckBox = request.getParameter("hideunavailable");
        if (StringUtils.isEmpty(hideUnavailableCheckBox)) {
            response.sendRedirect(request.getContextPath() + "lib-app?command=GET_BOOK_LIST&page="
                    + pageNumber);
        } else {
            response.sendRedirect(request.getContextPath() + "lib-app?command=GET_BOOK_LIST&hideunavailable=on&page="
                    + pageNumber);
        }

    }

    private void deleteBooks() {
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

    private int setPageNumber(int countOfPages) {
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong book page.", e);
        }
        pageNumber = Math.min(countOfPages, pageNumber);
        return pageNumber;
    }
}
