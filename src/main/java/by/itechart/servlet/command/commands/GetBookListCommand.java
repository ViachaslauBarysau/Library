package by.itechart.servlet.command.commands;

import by.itechart.entity.Book;
import by.itechart.service.BookService;
import by.itechart.service.impl.BookServiceImpl;
import by.itechart.servlet.command.LibraryCommand;
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

        List<Book> pageOfBooks = bookService.getPageOfBooks(pageNumber);

        request.setAttribute("books", pageOfBooks);
        request.setAttribute("pageCount", countOfPages);
        request.setAttribute("pageNumber", pageNumber);

        forward("mainpage");
    }
}
