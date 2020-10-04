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
import java.util.List;

/**
 * Processes requests of getting book list page.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBookListCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static volatile GetBookListCommand instance;

    public static synchronized GetBookListCommand getInstance() {
        GetBookListCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (GetBookListCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GetBookListCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Forwards to the book list page with necessary parameters.
     *
     * @throws ServletException in case of servlet failure
     * @throws IOException      in case of IO failure
     */
    @Override
    public void process() throws ServletException, IOException {
        int pageCount;
        int pageNumber;
        List<BookDto> books;
        if (StringUtils.isNotEmpty(request.getParameter("hideunavailable"))) {
            request.setAttribute("hideUnavailable", "true");
            pageCount = bookService.getAvailableBookPageCount();
            pageNumber = setPageNumber(pageCount);
            books = bookService.getAvailableBookPage(pageNumber);
        } else {
            pageCount = bookService.getAllBookPageCount();
            pageNumber = setPageNumber(pageCount);
            books = bookService.getAllBookPage(pageNumber);
        }
        request.setAttribute("books", books);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("pageNumber", pageNumber);
        forward("mainpage");
    }
}
