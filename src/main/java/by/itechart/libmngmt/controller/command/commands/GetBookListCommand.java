package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import lombok.Data;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetBookListCommand extends LibraryCommand {
    private final BookService bookService = BookServiceImpl.getInstance();
    private static GetBookListCommand instance;

    public static GetBookListCommand getInstance() {
        if(instance == null){
            instance = new GetBookListCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        final int MIN_PAGE_NUMBER = 1;
        int pageNumber = MIN_PAGE_NUMBER;
        int pageCount;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {

        }
        List<BookDto> books;
        if (request.getParameter("hideunavailable") != null &&
                !request.getParameter("hideunavailable").isEmpty()) {
            request.setAttribute("hideUnavailable", "true");
            pageCount = bookService.getAvailableBookPageCount();
            pageNumber = setPageNumber(pageNumber, pageCount);
            books = bookService.getAvailableBookPage(pageNumber);
        } else {
            pageCount = bookService.getPageCount();
            pageNumber = setPageNumber(pageNumber, pageCount);
            books = bookService.getBookPage(pageNumber);
        }
        request.setAttribute("books", books);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("pageNumber", pageNumber);
        forward("mainpage");
    }

    private int setPageNumber(int pageNumber, int pageCount) {
        if (pageNumber > pageCount) {
            pageNumber = pageCount;
        } else if (pageNumber < 1) {
            pageNumber = 1;
        }
        return pageNumber;
    }
}
