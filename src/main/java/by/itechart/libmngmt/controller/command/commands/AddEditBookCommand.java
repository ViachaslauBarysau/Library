package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import by.itechart.libmngmt.util.validator.ValidateExecutor;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;


public class AddEditBookCommand extends LibraryCommand {
    private BookManagementService bookManagementService = BookManagementServiceImpl.getInstance();
    private BookService bookService = BookServiceImpl.getInstance();
    private ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    private final static AddEditBookCommand instance = new AddEditBookCommand();

    public static AddEditBookCommand getInstance() {
        return instance;
    }

    @Override
    public void process() throws ServletException, IOException {


        int availableAmount = Integer.parseInt(request.getParameter("totalAmount"));
        try {
            availableAmount = (Integer.parseInt(request.getParameter("availableAmount")));

        } catch (Exception e) {

        }

        BookEntity bookEntity = BookEntity.builder()
                .id(Integer.parseInt(request.getParameter("id")))
                .title(request.getParameter("title"))
                .publisher(request.getParameter("publisher"))
                .publishDate(Integer.parseInt(request.getParameter("publishDate")))
                .pageCount(Integer.parseInt(request.getParameter("pageCount")))
                .genres(Arrays.asList(request.getParameterValues("genres")))
                .authors(Arrays.asList(request.getParameterValues("authors")))
                .ISBN(request.getParameter("ISBN"))
                .description(request.getParameter("description"))
                .totalAmount(Integer.parseInt(request.getParameter("totalAmount")))
                .availableAmount(availableAmount)
                .build();

        Part filePart = request.getPart("file");
        if (filePart.getSubmittedFileName().equals("")) {
            bookEntity.setCovers(Arrays.asList(request.getParameter("currentCover")));
        } else {
            String fileName = ValidateExecutor.validateAndUploadFile(filePart);
            if (fileName.equals("")) {
                bookEntity.setCovers(Arrays.asList(request.getParameter("currentCover")));
            } else {
                bookEntity.setCovers(Arrays.asList(fileName));
            }

        }

        int bookId = bookService.addEditBook(bookEntity);

        ReaderCardEntity readerCardEntity = new ReaderCardEntity();
        if (Integer.parseInt(request.getParameter("readerCardId")) > 0) {
            readerCardEntity = ReaderCardEntity.builder()
                    .id(Integer.parseInt(request.getParameter("readerCardId")))
                    .returnDate(Timestamp.valueOf(request.getParameter("returnDate")))
                    .comment(request.getParameter("comment"))
                    .build();
        }
        readerCardService.update(readerCardEntity);

        BookPageDto bookPageDto = bookManagementService.getBookPageDto(bookId);
        request.setAttribute("bookpagedto", bookPageDto);
        response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" + bookId);
    }
}
