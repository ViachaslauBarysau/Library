package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.util.FileUploader;
import by.itechart.libmngmt.util.converter.RequestExtractor;
import by.itechart.libmngmt.util.validation.BookValidator;
import by.itechart.libmngmt.util.validation.ReaderCardValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EditBookCommand extends LibraryCommand {
    public static final String EMPTY_STRING = "";
    public static final int COVER_INDEX = 0;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String CONTENT_TYPE = "application/json";
    public static final String ENCODING = "UTF-8";
    private FileUploader fileUploader = FileUploader.getInstance();
    private RequestExtractor requestExtractor = RequestExtractor.getInstance();
    private BookService bookService = BookServiceImpl.getInstance();
    private BookValidator bookValidator = BookValidator.getInstance();
    private ReaderCardValidator readerCardValidator = ReaderCardValidator.getInstance();
    private static volatile EditBookCommand instance;

    public static synchronized EditBookCommand getInstance() {
        EditBookCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (EditBookCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EditBookCommand();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void process() throws ServletException, IOException {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.addAll(bookValidator.validate(request).getErrorList());
        errorMessages.addAll(readerCardValidator.validate(request).getErrorList());
        boolean isValidationSuccessful = errorMessages.size() == 0;
        if (isValidationSuccessful) {
            editBook();
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id="
                    + request.getParameter("id"));
        } else {
            sendResponse(errorMessages);
        }
    }

    private void sendResponse(List<String> errorMessages) throws IOException {
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        String errors = gson.toJson(errorMessages);
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        out.print(errors);
        out.flush();
    }

    private void editBook() throws IOException, ServletException {
        BookDto bookDto = requestExtractor.extractBookDto(request);
        bookService.addEditBook(bookDto);
        boolean isFileAttached = !request.getPart("file").getSubmittedFileName().equals(EMPTY_STRING);
        if (isFileAttached) {
            fileUploader.uploadFile(request.getPart("file"), bookDto.getCovers().get(COVER_INDEX));
        }
    }
}

