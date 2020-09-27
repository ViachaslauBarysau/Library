package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.util.FileUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestExtractor {
    private FileUtility fileUtility = FileUtility.getInstance();
    private static volatile RequestExtractor instance;

    public static synchronized RequestExtractor getInstance() {
        RequestExtractor localInstance = instance;
        if (localInstance == null) {
            synchronized (RequestExtractor.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RequestExtractor();
                }
            }
        }
        return localInstance;
    }

    public BookDto extractBookDto(HttpServletRequest request) throws IOException, ServletException {
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
                .covers(Arrays.asList(fileUtility.getUniqueName(request)))
                .readerCardDtos(extractReaderCardDtoList(request))
                .build();
        return bookDto;
    }

    public List<ReaderCardDto> extractReaderCardDtoList(HttpServletRequest request) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        return new ArrayList<>(Arrays.asList(gson.fromJson
                (request.getParameter("readerCards"), ReaderCardDto[].class)));
    }
}
