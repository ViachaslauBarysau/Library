package by.itechart.libmngmt.servlet.command.commands;

import by.itechart.libmngmt.entity.Book;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.servlet.command.LibraryCommand;
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
        List<String> searchParams = new ArrayList<>();
        searchParams.add(request.getParameter("title"));
        searchParams.add(request.getParameter("author"));
        searchParams.add(request.getParameter("genre"));
        searchParams.add(request.getParameter("description"));
//        searchParams.add("%a%");
//        searchParams.add("%%");
//        searchParams.add("%com%");
//        searchParams.add("%%");

        List<Book> searchResult = bookService.searchBooks(searchParams);
        request.setAttribute("books", searchResult);
        forward("searchpage");
    }
}
