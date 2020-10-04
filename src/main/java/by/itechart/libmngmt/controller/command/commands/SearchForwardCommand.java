package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * Processes requests of search book.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchForwardCommand extends LibraryCommand {
    private static volatile SearchForwardCommand instance;

    public static synchronized SearchForwardCommand getInstance() {
        SearchForwardCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (SearchForwardCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SearchForwardCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Gets search parameters and sends redirect to the URL that contains
     * all of them for search executing.
     *
     * @throws IOException in case of IO failure
     */
    @Override
    public void process() throws IOException {
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_EXECUTE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description + "&page=" + pageNumber);
    }
}
