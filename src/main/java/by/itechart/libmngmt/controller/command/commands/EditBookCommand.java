package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.util.converter.RequestConverter;
import by.itechart.libmngmt.util.validator.BookValidator;
import by.itechart.libmngmt.util.validator.ReaderCardValidator;
import by.itechart.libmngmt.util.validator.fileValidator.FileUploader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditBookCommand extends LibraryCommand {
    private final FileUploader fileUploader = FileUploader.getInstance();
    private final RequestConverter requestConverter = RequestConverter.getInstance();
    private final BookService bookService = BookServiceImpl.getInstance();
    private static EditBookCommand instance;

    public static synchronized EditBookCommand getInstance() {
        if (instance == null) {
            instance = new EditBookCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        BookValidator bookValidator = new BookValidator();
        bookValidator.validate(request);
        String fileName = bookValidator.validateFile(request);
        List<String> readerCardsErrorMessages = validateReaderCards(request);
        List<String> errorMessages = bookValidator.getErrorMessages();
        errorMessages.addAll(readerCardsErrorMessages);
        if (errorMessages.size() < 1) {
            BookDto bookDto = requestConverter.convertToBookDto(request, fileName);
            bookDto.setReaderCardDtos(getReaderCards(request));
            bookService.addEditBook(bookDto);
            if (!request.getPart("file").equals("")) {
                fileUploader.uploadFile(request.getPart("file"), fileName);
            }
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" +
                    request.getParameter("id"));
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

    private List<String> validateReaderCards(HttpServletRequest request) {
        List<String> readerCardsErrorMessages = new ArrayList<>();
        if (request.getParameter("readerCards") != null && !request.getParameter("readerCards").isEmpty()) {
            ReaderCardValidator readerCardValidator = new ReaderCardValidator();
            List<ReaderCardDto> readerCards = getReaderCards(request);
            for (ReaderCardDto readerCardDto : readerCards) {
                readerCardValidator.validate(readerCardDto.getReaderEmail(), readerCardDto.getReaderName());
            }
            readerCardsErrorMessages.addAll(readerCardValidator.getErrorMessages());
        }
        return readerCardsErrorMessages;
    }

    private List<ReaderCardDto> getReaderCards(HttpServletRequest request) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return new ArrayList<>(Arrays.asList(gson.fromJson
                (request.getParameter("readerCards"), ReaderCardDto[].class)));
    }
}

