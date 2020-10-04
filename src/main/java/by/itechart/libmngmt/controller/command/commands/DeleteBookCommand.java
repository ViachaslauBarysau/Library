package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes deleting books requests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteBookCommand extends LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(DeleteBookCommand.class.getName());
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

    /**
     * Redirects to the book list page after deleting book(s) with necessary attributes.
     *
     * @throws IOException in case of IO failure
     */
    @Override
    public void process() throws IOException {
        deleteBooks();
        int countOfPages = bookService.getAllBookPageCount();
        int pageNumber = setPageNumber(countOfPages);
        String hideUnavailableCheckBoxParam = request.getParameter("hideunavailable");
        if (StringUtils.isEmpty(hideUnavailableCheckBoxParam)) {
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
}
