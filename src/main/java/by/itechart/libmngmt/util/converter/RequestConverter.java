package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.ReaderCardDto;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class RequestConverter {

    public static BookDto convertToBookDto(HttpServletRequest request, String fileName) {
        BookDto bookDto = BookDto.builder()
                .id(Integer.parseInt(request.getParameter("id")))
                .title(request.getParameter("title"))
                .publisher(request.getParameter("publisher"))
                .publishDate(Integer.parseInt(request.getParameter("publishDate")))
                .pageCount(Integer.parseInt(request.getParameter("pageCount")))
                .genres(Arrays.asList(request.getParameterValues("genres")))
                .authors(Arrays.asList(request.getParameterValues("authors")))
                .isbn(request.getParameter("ISBN"))
                .description(request.getParameter("description"))
                .totalAmount(Integer.parseInt(request.getParameter("totalAmount")))
                .covers(Arrays.asList(fileName))
                .build();
        return bookDto;
    }

    public static ReaderCardDto convertToReaderCardDto(HttpServletRequest request) {
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
                java.util.Date borrowDate = new SimpleDateFormat("MMM dd, yyyy")
                        .parse(request.getParameter("borrowDate"));
                java.util.Date dueDate = new SimpleDateFormat("MMM dd, yyyy")
                        .parse(request.getParameter("dueDate"));
                readerCardDto.setBorrowDate(new java.sql.Date(borrowDate.getTime()));
                readerCardDto.setDueDate(new java.sql.Date(dueDate.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            readerCardDto.setBorrowDate(Date.valueOf(request.getParameter("borrowDate")));
            readerCardDto.setDueDate(Date.valueOf(request.getParameter("dueDate")));
        }
        return readerCardDto;
    }


}
