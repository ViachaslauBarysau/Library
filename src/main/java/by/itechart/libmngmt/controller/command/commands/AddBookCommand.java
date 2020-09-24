package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.util.converter.RequestConverter;
import by.itechart.libmngmt.util.validator.BookValidator;
import by.itechart.libmngmt.util.validator.fileValidator.FileUploader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AddBookCommand extends LibraryCommand {
    private final FileUploader fileUploader = FileUploader.getInstance();
    private final RequestConverter requestConverter = RequestConverter.getInstance();
    private final BookService bookService = BookServiceImpl.getInstance();
    private static AddBookCommand instance;

    public static synchronized AddBookCommand getInstance() {
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
        List<String> errorMessages = bookValidator.getErrorMessages();
        if(errorMessages.size() < 1) {
            BookDto bookDto = requestConverter.convertToBookDto(request, fileName);
            int bookId = bookService.addEditBook(bookDto);
            if (!request.getPart("file").equals("")) {
                fileUploader.uploadFile(request.getPart("file"), fileName);
            }
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" + bookId);
        } else {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String errors = gson.toJson(errorMessages);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(errors);
            out.flush();
        }
    }
}
