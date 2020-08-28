package by.itechart.servlet.command.commands;

import by.itechart.entity.Book;
import by.itechart.service.BookService;
import by.itechart.service.impl.BookServiceImpl;
import by.itechart.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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

        Object[] booksIdsForDeleting = Arrays.stream(request.getParameterValues("bookid")).mapToInt(Integer::parseInt).boxed().toArray();

            bookService.deleteBooks(booksIdsForDeleting);

//        List<Book> pageOfBooks = bookService.getPageOfBooks(Integer.parseInt(request.getParameter("page")));
            List<Book> pageOfBooks = bookService.getPageOfBooks(1);
            request.setAttribute("books", pageOfBooks);
            request.setAttribute("pageCount", bookService.getCountOfPages());
            request.setAttribute("pageNumber", request.getParameter("page"));


        forward("mainpage");
    }
}
