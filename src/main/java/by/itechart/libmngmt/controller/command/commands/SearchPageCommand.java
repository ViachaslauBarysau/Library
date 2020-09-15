package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchPageCommand extends LibraryCommand {
    private final BookService bookService = BookServiceImpl.getInstance();
    private static SearchPageCommand instance;

    public static SearchPageCommand getInstance() {
        if(instance == null){
            instance = new SearchPageCommand();
        }
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
        String title = request.getParameter("title");
        title = (title != null && !title.isEmpty()) ? title : "";
        searchParams.add(title.toLowerCase());
        String author = request.getParameter("author");
        author = (author != null && !author.isEmpty()) ? author : "";
        searchParams.add(author.toLowerCase());
        String genre = request.getParameter("genre");
        genre = (genre != null && !genre.isEmpty()) ? genre : "";
        searchParams.add(genre.toLowerCase());
        String description = request.getParameter("description");
        description = (description != null && !description.isEmpty()) ? description : "";
        searchParams.add(description.toLowerCase());

        if (!title.equals("") || !author.equals("") || !genre.equals("") || !description.equals("")) {
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

            request.setAttribute("title", title);
            request.setAttribute("author", author);
            request.setAttribute("genre", genre);
            request.setAttribute("description", description);
        }
        forward("searchpage");
    }
}
