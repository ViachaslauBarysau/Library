package by.itechart.libmngmt.servlet.command.commands;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.dto.ReaderDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddBookPageCommand extends LibraryCommand {
    private BookService bookService = BookServiceImpl.getInstance();
    private static AddBookPageCommand instance = new AddBookPageCommand();

    public static AddBookPageCommand getInstance() {
        return instance;
    }


    @Override
    public void process() throws ServletException, IOException {
        BookDto bookDto = new BookDto();
        bookDto.setCovers(Arrays.asList("glass.jpg"));
        BookPageDto bookPageDto = BookPageDto.builder()
                .bookDto(bookDto)
                .readers(new HashMap<Integer, ReaderDto>())
                .readerCards(new ArrayList<ReaderCardDto>())
                .build();
        request.setAttribute("bookpagedto", bookPageDto);
//      For book adding test
//        BookEntity book = bookService.getBook(1);
//        book.getAuthors().add("ASsds");
//        book.getGenres().add("ASDAS");
//        bookService.addBook(book);

        forward("bookpage");
    }
}
