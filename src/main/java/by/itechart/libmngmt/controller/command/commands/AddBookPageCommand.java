package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddBookPageCommand extends LibraryCommand {
    private static AddBookPageCommand instance;

    public static AddBookPageCommand getInstance() {
        if(instance == null){
            instance = new AddBookPageCommand();
        }
        return instance;
    }


    @Override
    public void process() throws ServletException, IOException {

        BookDto bookDto = new BookDto();
        bookDto.setId(0);
        bookDto.setCovers(Arrays.asList("glass.jpg"));
        BookPageDto bookPageDto = BookPageDto.builder()
                .bookDto(bookDto)
                .readerCards(new ArrayList<>())
                .build();
        request.setAttribute("bookpagedto", bookPageDto);

        forward("bookpage");
    }
}
