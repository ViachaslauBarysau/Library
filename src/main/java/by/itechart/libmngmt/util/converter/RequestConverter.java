package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.BookDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class RequestConverter {
    private static RequestConverter instance;

    public static synchronized RequestConverter getInstance() {
        if(instance == null){
            instance = new RequestConverter();
        }
        return instance;
    }

    public BookDto convertToBookDto(HttpServletRequest request, String fileName) {
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
                .availableAmount(Integer.parseInt(request.getParameter("availableAmount")))
                .covers(Arrays.asList(fileName))
                .build();
        return bookDto;
    }
}
