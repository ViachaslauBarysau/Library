package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookPageDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.BookManagementService;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.BookManagementServiceImpl;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import by.itechart.libmngmt.util.validator.ValidateExecutor;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;


public class AddEditBookCommand extends LibraryCommand {
    private final ValidateExecutor validateExecutor= ValidateExecutor.getInstance();
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

        int availableAmount = Integer.parseInt(request.getParameter("totalAmount"));
        try {
            availableAmount = Integer.parseInt(request.getParameter("availableAmount"));

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
            String fileName = validateExecutor.validateAndUploadFile(filePart);
            if (fileName.equals("")) {
                bookEntity.setCovers(Arrays.asList(request.getParameter("currentCover")));
            } else {
                bookEntity.setCovers(Arrays.asList(fileName));
            }

        }

        if (Integer.parseInt(request.getParameter("readerCardId")) != -1) {
            ReaderCardDto readerCardDto = ReaderCardDto.builder()
                    .id(Integer.parseInt(request.getParameter("readerCardId")))
                    .bookId(Integer.parseInt(request.getParameter("id")))
                    .readerId(Integer.parseInt(request.getParameter("readerId")))
                    .readerEmail(request.getParameter("readerEmail"))
                    .readerName(request.getParameter("readerName"))
                    .status(request.getParameter("status"))
                    .comment(request.getParameter("comment"))
                    .build();

            if (!request.getParameter("status").equals("borrowed")) {
                readerCardDto.setReturnDate(Timestamp.valueOf(request.getParameter("returnDate")));
            }

            if (Integer.parseInt(request.getParameter("readerCardId")) > 0) {
                try {
                    java.util.Date borrowDate = new SimpleDateFormat("MMM dd, yyyy").parse(request.getParameter("borrowDate"));
                    java.util.Date dueDate = new SimpleDateFormat("MMM dd, yyyy").parse(request.getParameter("dueDate"));
                    readerCardDto.setBorrowDate(new java.sql.Date(borrowDate.getTime()));
                    readerCardDto.setDueDate(new java.sql.Date(dueDate.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                readerCardDto.setBorrowDate(Date.valueOf(request.getParameter("borrowDate")));
                readerCardDto.setDueDate(Date.valueOf(request.getParameter("dueDate")));
            }

            readerCardService.addOrUpdateReaderCard(readerCardDto);
        }


        int bookId = bookService.addEditBook(bookEntity);

        BookPageDto bookPageDto = bookManagementService.getBookPageDto(bookId);
        request.setAttribute("bookpagedto", bookPageDto);
        response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" + bookId);
    }
}
