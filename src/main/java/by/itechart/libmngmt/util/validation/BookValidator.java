package by.itechart.libmngmt.util.validation;

import by.itechart.libmngmt.controller.command.commands.EditBookCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class BookValidator {
    private static final String AUTHOR_GENRE_PATTERN = "^[a-zA-Z\\s]*$";
    private static final String NUMBER_PATTERN = "[0-9]+";
    private static final int MIN_DATE = 1900;
    private static final int MAX_PAGE_COUNT = 9999;
    private static final String JPEG_FORMAT = "image/jpeg";
    private static final String PNG_FORMAT = "image/png";
    private static final int MAX_FILE_SIZE = 2097152;
    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_LENGTH = 30;
    private static final String EMPTY_STRING = "";
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

    public ValidationResult validate(HttpServletRequest request) throws IOException, ServletException {
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
        ValidationResult validationResult = new ValidationResult(errorMessages);
        return validationResult;
    }

    private void validateTitle(String title, List<String> errorMessages) {
        if (StringUtils.isEmpty(title) || title.length() > MAX_TITLE_LENGTH) {
            errorMessages.add("Title is incorrect.");
        }
    }

    private void validateAuthors(List<String> authors, List<String> errorMessages) {
        if (authors != null) {
            Set<String> authorsSet = new HashSet<>();
            for (String name : authors) {
                String authorName = name.trim().toLowerCase();
                if (!StringUtils.isEmpty(authorName) && authorName.length() < MAX_LENGTH
                        && authorName.matches(AUTHOR_GENRE_PATTERN)) {
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

    private void validateGenres(List<String> genres, List<String> errorMessages) {
        if (genres != null) {
            Set<String> genresSet = new HashSet<>();
            for (String bookGenre : genres) {
                String genre = bookGenre.trim().toLowerCase();
                if (!StringUtils.isEmpty(genre) && genre.length() < MAX_LENGTH && genre.matches(AUTHOR_GENRE_PATTERN)) {
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

    private void validateIsbn(String isbn, List<String> errorMessages) {
        ISBNValidator isbnValidator = ISBNValidator.getInstance();
        if (StringUtils.isEmpty(isbn) || !isbnValidator.isValid(isbn)) {
            errorMessages.add("ISBN is not valid.");
        }
    }

    private void validatePublishDate(String date, List<String> errorMessages) {
        if (StringUtils.isEmpty(date) || !date.matches(NUMBER_PATTERN) || Integer.parseInt(date) < MIN_DATE ||
                Integer.parseInt(date) > LocalDate.now().getYear()) {
            errorMessages.add("Publish date is not valid.");
        }
    }

    private void validatePublisher(String publisher, List<String> errorMessages) {
        if (StringUtils.isEmpty(publisher) || publisher.length() > MAX_LENGTH ||
                !publisher.matches(AUTHOR_GENRE_PATTERN)) {
            errorMessages.add("Publisher name is incorrect.");
        }
    }

    private void validatePageCount(String pageCount, List<String> errorMessages) {
        if (StringUtils.isEmpty(pageCount) || !pageCount.matches(NUMBER_PATTERN) ||
                (Integer.parseInt(pageCount) <= MIN_COUNT || Integer.parseInt(pageCount) > MAX_PAGE_COUNT)) {
            errorMessages.add("Page count is not valid.");
        }
    }

    private void validateTotalAmount(String totalAmount, List<String> errorMessages) {
        if (StringUtils.isEmpty(totalAmount) || !totalAmount.matches(NUMBER_PATTERN) ||
                Integer.parseInt(totalAmount) < MIN_COUNT) {
            errorMessages.add("Total amount is not valid.");
        }
    }

    private void validateFile(HttpServletRequest request, List<String> errors) throws IOException, ServletException {
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
