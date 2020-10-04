package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes deleting searched books requests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteSearchedBookCommand extends LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(DeleteSearchedBookCommand.class.getName());
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

    /**
     * Redirects to the search page after deleting book(s) on the
     * search page with necessary attributes.
     *
     * @throws IOException in case of IO failure
     */
    @Override
    public void process() throws IOException {
        deleteBooks();
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_EXECUTE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description + "&page=" + pageNumber);
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
