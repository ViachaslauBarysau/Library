package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.controller.command.LibraryCommand;
import lombok.Data;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchBookCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static SearchBookCommand instance = new SearchBookCommand();

    public static SearchBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        int pageNumber = 1;

        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {

        }

        List<String> searchParams = new ArrayList<>();
        searchParams.add(request.getParameter("title"));
        searchParams.add(request.getParameter("author"));
        searchParams.add(request.getParameter("genre"));
        searchParams.add(request.getParameter("description"));

        if (!request.getParameter("title").equals("") || !request.getParameter("author").equals("")
        || !request.getParameter("genre").equals("") || !request.getParameter("description").equals("")) {
            int pageCount = bookService.getSearchPageCount(searchParams);

            if (pageNumber > pageCount) {
                pageNumber = pageCount;
            } else if (pageNumber < 1) {
                pageNumber = 1;
            }

            List<BookEntity> searchResult = bookService.search(searchParams, pageNumber);
            request.setAttribute("books", searchResult);

            request.setAttribute("pageCount", pageCount);
            request.setAttribute("pageNumber", pageNumber);

            request.setAttribute("title", request.getParameter("title"));
            request.setAttribute("author", request.getParameter("author"));
            request.setAttribute("genre", request.getParameter("genre"));
            request.setAttribute("description", request.getParameter("description"));
        }

        forward("searchpage");

    }
}
