package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class GetBookListCommand extends LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(GetBookListCommand.class.getName());
    private static final int MIN_PAGE_NUMBER = 1;
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

    @Override
    public void process() throws ServletException, IOException {
        int pageCount;
        int pageNumber;
        List<BookDto> books;
        if (!StringUtils.isEmpty(request.getParameter("hideunavailable"))) {
            request.setAttribute("hideUnavailable", "true");
            pageCount = bookService.getAvailableBookPageCount();
            pageNumber = setPageNumber(pageCount);
            books = bookService.getAvailableBookPage(pageNumber);
        } else {
            pageCount = bookService.getPageCount();
            pageNumber = setPageNumber(pageCount);
            books = bookService.getBookPage(pageNumber);
        }
        request.setAttribute("books", books);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("pageNumber", pageNumber);
        forward("mainpage");
    }

    private int setPageNumber(int pageCount) {
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong page number.", e);
        }
        if (pageNumber > pageCount) {
            pageNumber = pageCount;
        } else if (pageNumber < MIN_PAGE_NUMBER) {
            pageNumber = MIN_PAGE_NUMBER;
        }
        return pageNumber;
    }
}
