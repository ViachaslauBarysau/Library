package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.util.FileUploader;
import by.itechart.libmngmt.service.converter.Converter;
import by.itechart.libmngmt.service.converter.ConverterFactory;
import by.itechart.libmngmt.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes book editing requests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EditBookCommand extends LibraryCommand {
    private static final String EMPTY_STRING = "";
    private static final int COVER_INDEX = 0;
    private static final int BOOK_VALIDATOR_TYPE = 1;
    private static final int READER_CARD_VALIDATOR_TYPE = 2;
    private static final int REQUEST_BOOK_DTO_CONVERTER_TYPE = 4;
    private FileUploader fileUploader = FileUploader.getInstance();
    private Converter<BookDto, HttpServletRequest> requestBookDtoConverter
            = ConverterFactory.getInstance().createConverter(REQUEST_BOOK_DTO_CONVERTER_TYPE);
    private BookService bookService = BookServiceImpl.getInstance();
    private ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
    private static volatile EditBookCommand instance;

    public static synchronized EditBookCommand getInstance() {
        EditBookCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (EditBookCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EditBookCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Redirects to the book page if book was edited or sends
     * list of error messages if validation was failed.
     *
     * @throws ServletException in case of servlet failure
     * @throws IOException      in case of IO failure
     */
    @Override
    public void process() throws ServletException, IOException {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.addAll(validatorFactory.createValidator(BOOK_VALIDATOR_TYPE).validate(request).getErrorList());
        errorMessages.addAll(validatorFactory.createValidator(READER_CARD_VALIDATOR_TYPE).validate(request)
                .getErrorList());
        boolean isValidationSuccessful = errorMessages.size() == 0;
        if (isValidationSuccessful) {
            editBook();
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id="
                    + request.getParameter("id"));
        } else {
            sendResponse(errorMessages);
        }
    }

    private void editBook() throws IOException, ServletException {
        BookDto bookDto = requestBookDtoConverter.convertToDto(request);
        bookService.addEditBook(bookDto);
        boolean isFileAttached = !request.getPart("file").getSubmittedFileName().equals(EMPTY_STRING);
        if (isFileAttached) {
            fileUploader.uploadFile(request.getPart("file"), bookDto.getCovers().get(COVER_INDEX));
        }
    }
}

