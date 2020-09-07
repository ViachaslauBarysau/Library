package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import lombok.Data;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Data
public class GetBookListCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static GetBookListCommand instance = new GetBookListCommand();

    public static GetBookListCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

        int pageNumber = 1;

        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {

        }

        int pageCount = bookService.getPageCount();
        if (pageNumber>pageCount) {
            pageNumber = pageCount;
        } else if (pageNumber<1) {
            pageNumber = 1;
        }

        List<BookEntity> bookPage = bookService.getBookPage(pageNumber);

        request.setAttribute("books", bookPage);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("pageNumber", pageNumber);

        forward("mainpage");
    }
}
