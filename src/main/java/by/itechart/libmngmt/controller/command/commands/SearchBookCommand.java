package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import lombok.Data;

import javax.servlet.ServletException;
import java.io.IOException;

@Data
public class SearchBookCommand extends LibraryCommand {
    private final BookService bookService = BookServiceImpl.getInstance();
    private static SearchBookCommand instance;

    public static SearchBookCommand getInstance() {
        if(instance == null){
            instance = new SearchBookCommand();
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

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");

        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_PAGE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description +   "&page=" + pageNumber);
    }

}
