package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.util.converter.RequestConverter;
import by.itechart.libmngmt.util.validator.BookValidator;
import by.itechart.libmngmt.util.validator.fileValidator.FileUploader;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class AddBookCommand extends LibraryCommand {
    private final BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private final BookService bookService = BookServiceImpl.getInstance();
    private static AddBookCommand instance;

    public static AddBookCommand getInstance() {
        if(instance == null){
            instance = new AddBookCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        BookValidator bookValidator = new BookValidator();
        bookValidator.validate(request);
        String fileName = bookValidator.validateFile(request);
        List<String> errorMessages = bookValidator.errorMessages;
        if(errorMessages.size() < 1) {
            BookDto bookDto = RequestConverter.convertToBookDto(request, fileName);
            int bookId = bookService.addEditBook(bookDto);
            FileUploader.uploadFile(request.getPart("file"), fileName);
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" + bookId);
        } else {
            request.setAttribute("errors", errorMessages);
            BookPageDto bookPageDto = bookManagementService
                    .getBookPageDto(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("bookpagedto", bookPageDto);
            forward("bookpage");
        }
    }
}
