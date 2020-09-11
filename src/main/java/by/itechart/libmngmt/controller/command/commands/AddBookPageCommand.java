package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.controller.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AddBookPageCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static AddBookPageCommand instance = new AddBookPageCommand();

    public static AddBookPageCommand getInstance() {
        return instance;
    }


    @Override
    public void process() throws ServletException, IOException {

        BookDto bookDto = new BookDto();
        bookDto.setId(0);
        bookDto.setCovers(Arrays.asList("glass.jpg"));
        BookPageDto bookPageDto = BookPageDto.builder()
                .bookDto(bookDto)
                .readerCards(new ArrayList<ReaderCardDto>())
                .build();
        request.setAttribute("bookpagedto", bookPageDto);

        forward("bookpage");
    }
}
