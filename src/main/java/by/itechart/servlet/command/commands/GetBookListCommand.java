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
        List<Book> pageOfBooks = bookService.getPageOfBooks(Integer.parseInt(request.getParameter("page")));
        request.setAttribute("books", pageOfBooks);
        request.setAttribute("pageCount", bookService.getCountOfPages());
        request.setAttribute("pageNumber", request.getParameter("page"));
        forward("mainpage");
    }
}
