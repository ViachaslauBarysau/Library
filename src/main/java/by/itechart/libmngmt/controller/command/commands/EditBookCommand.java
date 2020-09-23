package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditBookCommand extends LibraryCommand {
    private final BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private final BookService bookService = BookServiceImpl.getInstance();
    private static EditBookCommand instance;

    public static EditBookCommand getInstance() {
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
        List<String> errorMessages = bookValidator.errorMessages;
        errorMessages.addAll(readerCardsErrorMessages);
        if (errorMessages.size() < 1) {
            BookDto bookDto = RequestConverter.convertToBookDto(request, fileName);
            bookDto.setReaderCardDtos(getReaderCards(request));
            bookService.addEditBook(bookDto);
            FileUploader.uploadFile(request.getPart("file"), fileName);
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" +
                    request.getParameter("id"));
        } else {
            request.setAttribute("errors", errorMessages);
            BookPageDto bookPageDto = bookManagementService
                    .getBookPageDto(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("bookpagedto", bookPageDto);
            forward("bookpage");
        }
    }

    private List<String> validateReaderCards(HttpServletRequest request) {
        List<String> readerCardsErrorMessages = new ArrayList<>();
        if (request.getParameter("readerCards") != null && !request.getParameter("readerCards").isEmpty()) {
            List<ReaderCardDto> readerCards = getReaderCards(request);
            ReaderCardValidator readerCardValidator = new ReaderCardValidator();
            for (ReaderCardDto readerCardDto : readerCards) {
                readerCardValidator.validate(readerCardDto.getReaderEmail(), readerCardDto.getReaderName());
            }
            readerCardsErrorMessages.addAll(readerCardValidator.errorMessages);
        }
        return readerCardsErrorMessages;
    }

    private List<ReaderCardDto> getReaderCards(HttpServletRequest request) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return new ArrayList<>(Arrays.asList(gson.fromJson
                (request.getParameter("readerCards"), ReaderCardDto[].class)));
    }
}

