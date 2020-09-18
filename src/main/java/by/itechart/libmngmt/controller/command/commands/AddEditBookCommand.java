package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import by.itechart.libmngmt.util.converter.RequestConverter;
import by.itechart.libmngmt.util.validator.BookValidator;
import by.itechart.libmngmt.util.validator.ReaderCardValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddEditBookCommand extends LibraryCommand {

    private final BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    private static AddEditBookCommand instance;

    public static AddEditBookCommand getInstance() {
        if(instance == null){
            instance = new AddEditBookCommand();
        }
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {
        boolean isReaderCardChanged = Integer.parseInt(request.getParameter("readerCardId")) != -1;
        BookValidator bookValidator = new BookValidator();
        bookValidator.validate(request);
        String fileName = bookValidator.validateFile(request);

        ReaderCardDto readerCardDto = new ReaderCardDto();
        List<String> readerCardsErrorMessages = new ArrayList<>();

        if (isReaderCardChanged) {
            ReaderCardValidator readerCardValidator = new ReaderCardValidator();
            readerCardValidator.validate(request);
            readerCardsErrorMessages = readerCardValidator.errorMessages;
            readerCardDto = RequestConverter.convertToReaderCardDto(request);
        }

        List<String> errorMessages = new ArrayList<>(bookValidator.errorMessages);
        errorMessages.addAll(readerCardsErrorMessages);

        if(errorMessages.size() < 1) {
            if (Integer.parseInt(request.getParameter("readerCardId")) != -1) {
                readerCardService.addOrUpdateReaderCard(readerCardDto);
            }
            BookDto bookDto = RequestConverter.convertToBookDto(request, fileName);

            int bookId = bookService.addEditBook(bookDto);
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" + bookId);
        } else {
            request.setAttribute("errors", errorMessages);
            if (Integer.parseInt(request.getParameter("id")) > 0) {
                BookPageDto bookPageDto = bookManagementService
                        .getBookPageDto(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("bookpagedto", bookPageDto);

            } else {
                BookDto bookDto = new BookDto();
                bookDto.setId(0);
                bookDto.setCovers(Arrays.asList("glass.jpg"));
                BookPageDto bookPageDto = BookPageDto.builder()
                        .bookDto(bookDto)
                        .readerCards(new ArrayList<>())
                        .build();
                request.setAttribute("bookpagedto", bookPageDto);

            }
        }
        forward("bookpage");
    }
}

