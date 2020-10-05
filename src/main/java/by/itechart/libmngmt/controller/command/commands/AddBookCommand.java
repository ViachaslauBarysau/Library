package by.itechart.libmngmt.controller.command.commands;

import by.itechart.libmngmt.controller.command.LibraryCommand;
import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.converter.Converter;
import by.itechart.libmngmt.service.converter.ConverterFactory;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.util.FileUploader;
import by.itechart.libmngmt.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Processes requests of adding new book.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddBookCommand extends LibraryCommand {
    private static final String EMPTY_STRING = "";
    private static final int BOOK_VALIDATOR_NUMBER = 1;
    private static final int COVER_INDEX = 0;
    private static final int REQUEST_BOOK_DTO_CONVERTER_TYPE = 4;
    private FileUploader fileUploader = FileUploader.getInstance();
    private Converter<BookDto, HttpServletRequest> requestBookDtoConverter
            = ConverterFactory.getInstance().createConverter(REQUEST_BOOK_DTO_CONVERTER_TYPE);
    private BookService bookService = BookServiceImpl.getInstance();
    private ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
    private static volatile AddBookCommand instance;

    public static synchronized AddBookCommand getInstance() {
        AddBookCommand localInstance = instance;
        if (localInstance == null) {
            synchronized (AddBookCommand.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AddBookCommand();
                }
            }
        }
        return localInstance;
    }

    /**
     * Redirects to the book page if book was added or sends
     * list of error messages if validation was failed.
     *
     * @throws ServletException in case of servlet failure
     * @throws IOException      in case of IO failure
     */
    @Override
    public void process() throws ServletException, IOException {
        List<String> errorMessages = validatorFactory.createValidator(BOOK_VALIDATOR_NUMBER).validate(request)
                .getErrorList();
        boolean isValidationSuccessful = errorMessages.size() == 0;
        if (isValidationSuccessful) {
            int bookId = addBook();
            response.sendRedirect(request.getContextPath() + "/lib-app?command=BOOK_PAGE&id=" + bookId);
        } else {
            sendResponse(errorMessages);
        }
    }

    private int addBook() throws IOException, ServletException {
        BookDto bookDto = requestBookDtoConverter.convertToDto(request);
        int bookId = bookService.addEditBook(bookDto);
        boolean isFileAttached = !request.getPart("file").getSubmittedFileName().equals(EMPTY_STRING);
        if (isFileAttached) {
            fileUploader.uploadFile(request.getPart("file"), bookDto.getCovers().get(COVER_INDEX));
        }
        return bookId;
    }
}