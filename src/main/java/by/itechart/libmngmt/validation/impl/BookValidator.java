package by.itechart.libmngmt.validation.impl;

import by.itechart.libmngmt.validation.ValidationResult;
import by.itechart.libmngmt.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Provides methods for validation request parameters.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookValidator implements Validator {
    private static final String NUMBER_PATTERN = "[0-9]+";
    private static final String JPEG_FORMAT = "image/jpeg";
    private static final String PNG_FORMAT = "image/png";
    private static final String EMPTY_STRING = "";
    private static final int MIN_DATE = 0;
    private static final int MAX_PAGE_COUNT = 9999;
    private static final int MAX_FILE_SIZE = 2097152;
    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_LENGTH = 30;
    public static final int MIN_COUNT = 0;
    private static BookValidator instance;

    public static synchronized BookValidator getInstance() {
        BookValidator localInstance = instance;
        if (localInstance == null) {
            synchronized (BookValidator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BookValidator();
                }
            }
        }
        return localInstance;
    }

    /**
     * Returns ValidationResult object that contains list with
     * validation error messages.
     *
     * @param request HttpServletRequest
     * @return ValidationResult object
     */
    public ValidationResult validate(final HttpServletRequest request) throws IOException, ServletException {
        List<String> errorMessages = new ArrayList<>();
        validateTitle(request.getParameter("title"), errorMessages);
        validateAuthors(Arrays.asList(request.getParameterValues("authors")), errorMessages);
        validateGenres(Arrays.asList(request.getParameterValues("genres")), errorMessages);
        validateIsbn(request.getParameter("ISBN"), errorMessages);
        validatePublishDate(request.getParameter("publishDate"), errorMessages);
        validatePublisher(request.getParameter("publisher"), errorMessages);
        validatePageCount(request.getParameter("pageCount"), errorMessages);
        validateTotalAmount(request.getParameter("totalAmount"), errorMessages);
        validateFile(request, errorMessages);
        return ValidationResult.builder()
                .errorList(errorMessages)
                .build();
    }

    private void validateTitle(final String title, final List<String> errorMessages) {
        if (StringUtils.isEmpty(title) || title.length() > MAX_TITLE_LENGTH) {
            errorMessages.add("Title is incorrect.");
        }
    }

    private void validateAuthors(final List<String> authors, final List<String> errorMessages) {
        if (authors != null) {
            Set<String> authorsSet = new HashSet<>();
            for (String name : authors) {
                String authorName = name.trim().toLowerCase();
                if (StringUtils.isNotEmpty(authorName) && authorName.length() < MAX_LENGTH) {
                    authorsSet.add(authorName);
                }
            }
            if (authors.size() != authorsSet.size()) {
                errorMessages.add("Author's name(s) is incorrect.");
            }
        } else {
            errorMessages.add("Author's name(s) is incorrect.");
        }
    }

    private void validateGenres(final List<String> genres, final List<String> errorMessages) {
        if (genres != null) {
            Set<String> genresSet = new HashSet<>();
            for (String bookGenre : genres) {
                String genre = bookGenre.trim().toLowerCase();
                if (StringUtils.isNotEmpty(genre) && genre.length() < MAX_LENGTH) {
                    genresSet.add(genre);
                }
            }
            if (genres.size() != genresSet.size()) {
                errorMessages.add("Genre(s) is incorrect.");
            }
        } else {
            errorMessages.add("Genre(s) is incorrect.");
        }
    }

    private void validateIsbn(final String isbn, final List<String> errorMessages) {
        ISBNValidator isbnValidator = ISBNValidator.getInstance();
        if (StringUtils.isEmpty(isbn) || !isbnValidator.isValid(isbn)) {
            errorMessages.add("ISBN is not valid.");
        }
    }

    private void validatePublishDate(final String date, final List<String> errorMessages) {
        if (StringUtils.isEmpty(date) || !date.matches(NUMBER_PATTERN) || Integer.parseInt(date) < MIN_DATE
                || (Integer.parseInt(date) > LocalDate.now().getYear())) {
            errorMessages.add("Publish date is not valid.");
        }
    }

    private void validatePublisher(final String publisher, final List<String> errorMessages) {
        if (StringUtils.isEmpty(publisher) || publisher.length() > MAX_LENGTH) {
            errorMessages.add("Publisher name is incorrect.");
        }
    }

    private void validatePageCount(final String pageCount, final List<String> errorMessages) {
        if (StringUtils.isEmpty(pageCount) || !pageCount.matches(NUMBER_PATTERN)
                || (Integer.parseInt(pageCount) <= MIN_COUNT || Integer.parseInt(pageCount) > MAX_PAGE_COUNT)) {
            errorMessages.add("Page count is not valid.");
        }
    }

    private void validateTotalAmount(final String totalAmount, final List<String> errorMessages) {
        if (StringUtils.isEmpty(totalAmount) || !totalAmount.matches(NUMBER_PATTERN)
                || Integer.parseInt(totalAmount) < MIN_COUNT) {
            errorMessages.add("Total amount is not valid.");
        }
    }

    private void validateFile(final HttpServletRequest request, final List<String> errors) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        long fileSize = filePart.getSize();
        String fileType = filePart.getContentType();
        if (!filePart.getSubmittedFileName().equals(EMPTY_STRING)) {
            if (!(fileSize <= MAX_FILE_SIZE) || !(fileType.equals(JPEG_FORMAT) || fileType.equals(PNG_FORMAT))) {
                errors.add("Only jpeg/png up to 2Mb for image.");
            }
        }
    }
}
