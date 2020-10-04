package by.itechart.libmngmt.service.converter.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.util.FileUtility;
import by.itechart.libmngmt.service.converter.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * Uses for getting BookDto from request.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestBookDtoConverter implements Converter<BookDto, HttpServletRequest> {
    private static final Logger LOGGER = LogManager.getLogger(RequestBookDtoConverter.class.getName());
    private RequestReaderCardDtoListConverter requestReaderCardDtoListConverter
            = RequestReaderCardDtoListConverter.getInstance();
    private FileUtility fileUtility = FileUtility.getInstance();
    private static volatile RequestBookDtoConverter instance;

    public static synchronized RequestBookDtoConverter getInstance() {
        RequestBookDtoConverter localInstance = instance;
        if (localInstance == null) {
            synchronized (RequestBookDtoConverter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RequestBookDtoConverter();
                }
            }
        }
        return localInstance;
    }

    /**
     * Returns BookDto getting from HttpServletRequest.
     *
     * @param request HttpServletRequest object
     * @return BookDto object
     */
    @Override
    public BookDto convertToDto(final HttpServletRequest request) {
        try {
            return BookDto.builder()
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
                    .readerCardDtos(requestReaderCardDtoListConverter.convertToDto(request))
                    .build();
        } catch (IOException e) {
            LOGGER.debug("Convert request to BookDto error.", e);
        } catch (ServletException e) {
            LOGGER.debug("Convert request to BookDto error.", e);
        }
        return null;
    }

    /**
     * Method never used.
     */
    @Override
    public HttpServletRequest convertToEntity(final BookDto object) {
        //No need to override because of uselessness.
        return null;
    }
}
