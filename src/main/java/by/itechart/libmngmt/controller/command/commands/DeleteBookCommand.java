package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.controller.command.LibraryCommand;

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
            bookService.delete(booksIdsForDeleting);
        } catch (Exception e) {

        }

        int countOfPages = bookService.getPageCount();
        int pageNumber = Math.min(countOfPages, Integer.parseInt(request.getParameter("page")));
        List<BookEntity> pageOfBooks = bookService.getBookPage(pageNumber);

        request.setAttribute("books", pageOfBooks);
        request.setAttribute("pageCount", countOfPages);
        request.setAttribute("pageNumber", pageNumber);

        forward("mainpage");
    }
}
