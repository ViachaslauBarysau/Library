package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Processes requests of opening page for adding book.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddBookPageCommand extends LibraryCommand {
    private static final String DEFAULT_COVER = "glass.jpg";
    private static final int NEW_BOOK_ID = 0;
    private static volatile AddBookPageCommand instance;

    public static synchronized AddBookPageCommand getInstance() {
        AddBookPageCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (AddBookPageCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AddBookPageCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Forwards to the adding book page  with necessary attributes.
     *
     * @throws ServletException in case of servlet failure
     * @throws IOException      in case of IO failure
     */
    @Override
    public void process() throws ServletException, IOException {
        BookPageDto bookPageDto = getEmptyPageBookPageDto();
        request.setAttribute("bookpagedto", bookPageDto);
        forward("bookpage");
    }

    private BookPageDto getEmptyPageBookPageDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(NEW_BOOK_ID);
        bookDto.setCovers(Arrays.asList(DEFAULT_COVER));
        return BookPageDto.builder()
                .bookDto(bookDto)
                .readerCards(new ArrayList<>())
                .build();
    }
}
