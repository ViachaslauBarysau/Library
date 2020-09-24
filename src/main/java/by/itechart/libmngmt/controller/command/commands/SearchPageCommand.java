package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchPageCommand extends LibraryCommand {
    private final static Logger LOGGER = LogManager.getLogger(SearchPageCommand.class.getName());
    private final BookService bookService = BookServiceImpl.getInstance();
    private static SearchPageCommand instance;
    private final int MIN_PAGE_NUMBER = 1;

    public static synchronized SearchPageCommand getInstance() {
        if(instance == null){
            instance = new SearchPageCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {

        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong page number.", e);
        }

        List<String> searchParams = new ArrayList<>();
        String title = request.getParameter("title");
        title = (title != null && !title.isEmpty()) ? title : "";
        searchParams.add(title);
        String author = request.getParameter("author");
        author = (author != null && !author.isEmpty()) ? author : "";
        searchParams.add(author);
        String genre = request.getParameter("genre");
        genre = (genre != null && !genre.isEmpty()) ? genre : "";
        searchParams.add(genre);
        String description = request.getParameter("description");
        description = (description != null && !description.isEmpty()) ? description : "";
        searchParams.add(description);

        if (!title.equals("") || !author.equals("") || !genre.equals("") || !description.equals("")) {
            int pageCount = bookService.getSearchPageCount(searchParams);
            if (pageNumber > pageCount) {
                pageNumber = pageCount;
            } else if (pageNumber < MIN_PAGE_NUMBER) {
                pageNumber = MIN_PAGE_NUMBER;
            }
            List<BookDto> searchResult = bookService.search(searchParams, pageNumber);
            request.setAttribute("books", searchResult);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("title", title);
            request.setAttribute("author", author);
            request.setAttribute("genre", genre);
            request.setAttribute("description", description);
        }
        forward("searchpage");
    }
}
