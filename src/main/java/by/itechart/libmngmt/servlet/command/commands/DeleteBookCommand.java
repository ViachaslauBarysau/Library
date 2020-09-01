package by.itechart.libmngmt.servlet.command.commands;

import by.itechart.libmngmt.entity.Book;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DeleteBookCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static DeleteBookCommand instance = new DeleteBookCommand();

    public static DeleteBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

        try {
            Object[] booksIdsForDeleting = Arrays.stream(request.getParameterValues("bookid")).mapToInt(Integer::parseInt).boxed().toArray();
            bookService.deleteBooks(booksIdsForDeleting);
        } catch (Exception e) {

        }

        int countOfPages = bookService.getCountOfPages();
        int pageNumber = Math.min(countOfPages, Integer.parseInt(request.getParameter("page")));
        List<Book> pageOfBooks = bookService.getPageOfBooks(pageNumber);

        request.setAttribute("books", pageOfBooks);
        request.setAttribute("pageCount", countOfPages);
        request.setAttribute("pageNumber", pageNumber);

        forward("mainpage");
    }
}
