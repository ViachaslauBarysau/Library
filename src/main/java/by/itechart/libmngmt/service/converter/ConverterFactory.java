package by.itechart.libmngmt.service.converter;

import by.itechart.libmngmt.service.converter.impl.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Converter factory for getting needed converter.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConverterFactory<A, B> {
    private static final Logger LOGGER = LogManager.getLogger(ConverterFactory.class.getName());
    private static final int BOOK_DTO_ENTITY_CONVERTER = 1;
    private static final int READER_CARD_DTO_ENTITY_CONVERTER = 2;
    private static final int READER_DTO_ENTITY_CONVERTER = 3;
    private static final int REQUEST_BOOK_DTO_CONVERTER = 4;
    private static final int REQUEST_READER_CARD_DTO_LIST_CONVERTER = 5;
    private BookDtoEntityConverter bookDtoEntityConverter = BookDtoEntityConverter.getInstance();
    private ReaderCardDtoEntityConverter readerCardDtoEntityConverter = ReaderCardDtoEntityConverter.getInstance();
    private ReaderDtoEntityConverter readerDtoEntityConverter = ReaderDtoEntityConverter.getInstance();
    private RequestBookDtoConverter requestBookDtoConverter = RequestBookDtoConverter.getInstance();
    private RequestReaderCardDtoListConverter requestReaderCardDtoListConverter
            = RequestReaderCardDtoListConverter.getInstance();
    private static volatile ConverterFactory instance;

    public static synchronized ConverterFactory getInstance() {
        ConverterFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (ConverterFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConverterFactory();
                }
            }
        }
        return localInstance;
    }

    /**
     * Returns converter depending on the converter type index.
     *
     * @param converterType converter type index
     * @return Converter object
     */
    public Converter<? extends Object, ? extends Object> createConverter(int converterType) {
        switch (converterType) {
            case (BOOK_DTO_ENTITY_CONVERTER): {
                return bookDtoEntityConverter;
            }
            case (READER_CARD_DTO_ENTITY_CONVERTER): {
                return readerCardDtoEntityConverter;
            }
            case (READER_DTO_ENTITY_CONVERTER): {
                return readerDtoEntityConverter;
            }
            case (REQUEST_BOOK_DTO_CONVERTER): {
                return requestBookDtoConverter;
            }
            case (REQUEST_READER_CARD_DTO_LIST_CONVERTER): {
                return requestReaderCardDtoListConverter;
            }
            default: {
                LOGGER.debug("This converter is unknown.");
                throw new RuntimeException("This converter is unknown.");
            }
        }
    }
}
