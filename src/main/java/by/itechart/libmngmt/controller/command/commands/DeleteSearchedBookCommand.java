package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteSearchedBookCommand extends LibraryCommand {
    private final BookService bookService = BookServiceImpl.getInstance();
    private static DeleteSearchedBookCommand instance;

    public static DeleteSearchedBookCommand getInstance() {
        if(instance == null){
            instance = new DeleteSearchedBookCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        final int MIN_PAGE_NUMBER = 1;
        try {
            List<Integer> booksIdsForDeleting = Arrays.asList(request.getParameterValues("bookid")).stream()
                    .mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            bookService.delete(booksIdsForDeleting);
        } catch (Exception e) {

        }

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {

        }
        response.sendRedirect(request.getContextPath() + "lib-app?command=SEARCH_PAGE&title=" + title +
                "&author=" + author + "&genre=" + genre + "&description=" + description +   "&page=" + pageNumber);
    }
}
