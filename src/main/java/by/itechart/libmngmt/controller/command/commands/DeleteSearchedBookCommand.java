package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteSearchedBookCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static DeleteSearchedBookCommand instance = new DeleteSearchedBookCommand();

    public static DeleteSearchedBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        try {
            Object[] booksIdsForDeleting = Arrays.stream(request.getParameterValues("bookid")).mapToInt(Integer::parseInt).boxed().toArray();
            bookService.delete(booksIdsForDeleting);
        } catch (Exception e) {

        }

        List<String> searchParams = new ArrayList<>();
        searchParams.add(request.getParameter("title"));
        searchParams.add(request.getParameter("author"));
        searchParams.add(request.getParameter("genre"));
        searchParams.add(request.getParameter("description"));

        int pageNumber = 1;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {

        }

        int pageCount = bookService.getSearchPageCount(searchParams);
        pageNumber = Math.min(pageCount, pageNumber);

        List<BookEntity> searchResult = bookService.search(searchParams, pageNumber);
        request.setAttribute("books", searchResult);

        request.setAttribute("pageCount", pageCount);
        request.setAttribute("pageNumber", pageNumber);

        request.setAttribute("title", request.getParameter("title"));
        request.setAttribute("author", request.getParameter("author"));
        request.setAttribute("genre", request.getParameter("genre"));
        request.setAttribute("description", request.getParameter("description"));

        forward("searchpage");
    }
}
