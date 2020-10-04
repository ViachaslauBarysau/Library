package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes requests of search executing.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchExecuteCommand extends LibraryCommand {
    private static final String EMPTY_STRING = "";
    private static final int TITLE_INDEX = 0;
    private static final int AUTHOR_INDEX = 1;
    private static final int GENRE_INDEX = 2;
    private static final int DESCRIPTION_INDEX = 3;
    private BookService bookService = BookServiceImpl.getInstance();
    private static volatile SearchExecuteCommand instance;

    public static synchronized SearchExecuteCommand getInstance() {
        SearchExecuteCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (SearchExecuteCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SearchExecuteCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Invokes search of book by search parameters and forwards to the
     * search page with search results.
     *
     * @throws ServletException in case of servlet failure
     * @throws IOException      in case of IO failure
     */
    @Override
    public void process() throws ServletException, IOException {
        List<String> searchParams = getSearchParams();
        if (!searchParams.get(TITLE_INDEX).equals(EMPTY_STRING)
                || !searchParams.get(AUTHOR_INDEX).equals(EMPTY_STRING)
                || !searchParams.get(GENRE_INDEX).equals(EMPTY_STRING)
                || !searchParams.get(DESCRIPTION_INDEX).equals(EMPTY_STRING)) {
            int pageCount = bookService.getSearchPageCount(searchParams);
            int pageNumber = setPageNumber(pageCount);
            List<BookDto> searchResult = bookService.search(searchParams, pageNumber);
            request.setAttribute("books", searchResult);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("title", searchParams.get(TITLE_INDEX));
            request.setAttribute("author", searchParams.get(AUTHOR_INDEX));
            request.setAttribute("genre", searchParams.get(GENRE_INDEX));
            request.setAttribute("description", searchParams.get(DESCRIPTION_INDEX));
        }
        forward("searchpage");
    }

    private List<String> getSearchParams() {
        List<String> searchParams = new ArrayList<>();
        String title = StringUtils.isEmpty(request.getParameter("title"))
                ? EMPTY_STRING : request.getParameter("title");
        searchParams.add(title);
        String author = StringUtils.isEmpty(request.getParameter("author"))
                ? EMPTY_STRING : request.getParameter("author");
        searchParams.add(author);
        String genre = StringUtils.isEmpty(request.getParameter("genre"))
                ? EMPTY_STRING : request.getParameter("genre");
        searchParams.add(genre);
        String description = StringUtils.isEmpty(request.getParameter("description"))
                ? EMPTY_STRING : request.getParameter("description");
        searchParams.add(description);
        return searchParams;
    }
}
