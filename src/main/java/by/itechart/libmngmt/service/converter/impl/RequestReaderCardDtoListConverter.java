package by.itechart.libmngmt.service.converter.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.converter.Converter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Uses for getting list of ReaderBookDto from request.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestReaderCardDtoListConverter implements Converter<List<ReaderCardDto>, HttpServletRequest> {
    private static volatile RequestReaderCardDtoListConverter instance;

    public static synchronized RequestReaderCardDtoListConverter getInstance() {
        RequestReaderCardDtoListConverter localInstance = instance;
        if (localInstance == null) {
            synchronized (RequestReaderCardDtoListConverter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RequestReaderCardDtoListConverter();
                }
            }
        }
        return localInstance;
    }

    /**
     * Returns list of ReaderCardDto getting from HttpServletRequest.
     *
     * @param request HttpServletRequest object
     * @return list of ReaderCardDto
     */
    @Override
    public List<ReaderCardDto> convertToDto(final HttpServletRequest request) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        return new ArrayList<>(Arrays.asList(gson.fromJson(request.getParameter("readerCards"),
                ReaderCardDto[].class)));
    }

    /**
     * Method never used.
     */
    @Override
    public HttpServletRequest convertToEntity(final List<ReaderCardDto> object) {
        //No need to override because of uselessness.
        return null;
    }
}
