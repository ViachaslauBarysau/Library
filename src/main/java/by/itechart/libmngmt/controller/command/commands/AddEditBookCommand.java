package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.util.validator.ValidateExecutor;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Arrays;


public class AddEditBookCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static AddEditBookCommand instance = new AddEditBookCommand();

    public static AddEditBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        int id = 0;
        try {
            id= (Integer.parseInt(request.getParameter("id")));

        } catch (Exception e) {

        }
        BookDto bookDto = BookDto.builder()
                .id(id)
                .title(request.getParameter("title"))
                .publisher(request.getParameter("publisher"))
                .publishDate(Integer.parseInt(request.getParameter("publishDate")))
                .pageCount(Integer.parseInt(request.getParameter("pageCount")))
                .genres(Arrays.asList(request.getParameterValues("genres")))
                .authors(Arrays.asList(request.getParameterValues("authors")))
                .ISBN(request.getParameter("ISBN"))
                .description(request.getParameter("description"))
                .totalAmount(Integer.parseInt(request.getParameter("totalAmount")))
                .availableAmount(Integer.parseInt(request.getParameter("totalAmount"))) // Need to fix it because of book adding(available amount hidden input is null)
//                .availableAmount(Integer.parseInt(request.getParameter("availableAmount")))
                .build();

        Part filePart = request.getPart("file");
        if (filePart.getSubmittedFileName().equals("")) {
            bookDto.setCovers(Arrays.asList(request.getParameter("currentCover")));
        } else {
            String fileName = ValidateExecutor.validateAndUploadFile(filePart);
            if (fileName.equals("")) {
                bookDto.setCovers(Arrays.asList(request.getParameter("currentCover")));
            } else {
                bookDto.setCovers(Arrays.asList(fileName));
            }

        }

        bookService.addEditBook(bookDto);

    }
}
