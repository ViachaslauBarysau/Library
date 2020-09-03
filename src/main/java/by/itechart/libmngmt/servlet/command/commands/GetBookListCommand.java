package by.itechart.libmngmt.servlet.command.commands;

import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.servlet.command.LibraryCommand;
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

        int countOfPages = bookService.getCountOfPages();
        if (pageNumber>countOfPages) {
            pageNumber = countOfPages;
        } else if (pageNumber<1) {
            pageNumber = 1;
        }

        List<BookEntity> pageOfBooks = bookService.getPageOfBooks(pageNumber);

        request.setAttribute("books", pageOfBooks);
        request.setAttribute("pageCount", countOfPages);
        request.setAttribute("pageNumber", pageNumber);

        forward("mainpage");
    }
}
