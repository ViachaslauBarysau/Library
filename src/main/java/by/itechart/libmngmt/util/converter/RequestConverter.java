package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.BookDto;

import javax.servlet.http.HttpServletRequest;
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
}
