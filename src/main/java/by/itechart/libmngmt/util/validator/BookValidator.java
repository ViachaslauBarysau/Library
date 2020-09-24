package by.itechart.libmngmt.util.validator;

import by.itechart.libmngmt.repository.AuthorRepository;
import by.itechart.libmngmt.repository.impl.AuthorRepositoryImpl;
import by.itechart.libmngmt.util.validator.fileValidator.Validator;
import by.itechart.libmngmt.util.validator.fileValidator.ValidatorFactory;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Getter
public class BookValidator {
    private final ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
    private List<String> errorMessages = new ArrayList<>();
    public static final String AUTHOR_GENRE_PATTERN = "^[a-zA-Z\\s]*$";
    public static final String NUMBER_PATTERN = "[0-9]+";
    final int SIZE_VALIDATOR = 1;
    final int TYPE_VALIDATOR = 2;
    final int NAME_VALIDATOR = 3;
    final int MIN_DATE = 1900;
    final int MAX_PAGE_COUNT = 9999;

    public void validate(HttpServletRequest request) {
        validateTitle(request.getParameter("title"));
        validateAuthors(Arrays.asList(request.getParameterValues("authors")));
        validateGenres(Arrays.asList(request.getParameterValues("genres")));
        validateIsbn(request.getParameter("ISBN"));
        validatePublishDate(request.getParameter("publishDate"));
        validatePublisher(request.getParameter("publisher"));
        validatePageCount(request.getParameter("pageCount"));
        validateTotalAmount(request.getParameter("totalAmount"));
    }

    private void validateTitle(String title) {
        if (StringUtils.isEmpty(title) || title.length() > 40) {
            errorMessages.add("Title is incorrect.");
        }
    }

    private void validateAuthors(List<String> authors) {
        if (authors != null) {
            Set<String> authorsSet = new HashSet<>();
            for (String name : authors) {
                String authorName = name.trim().toLowerCase();
                if (!StringUtils.isEmpty(authorName) && authorName.length() < 30
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

    private void validateGenres(List<String> genres) {
        if (genres != null) {
            Set<String> genresSet = new HashSet<>();
            for (String bookGenre : genres) {
                String genre = bookGenre.trim().toLowerCase();
                if (!StringUtils.isEmpty(genre) && genre.length() < 30 && genre.matches(AUTHOR_GENRE_PATTERN)) {
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

    private void validateIsbn(String isbn) {
        ISBNValidator isbnValidator = ISBNValidator.getInstance();
        if (StringUtils.isEmpty(isbn) || !isbnValidator.isValid(isbn)) {
            errorMessages.add("ISBN is not valid.");
        }
    }

    private void validatePublishDate(String date) {
        if (StringUtils.isEmpty(date) || !date.matches(NUMBER_PATTERN) || Integer.parseInt(date) < MIN_DATE ||
                Integer.parseInt(date) > LocalDate.now().getYear()){
            errorMessages.add("Publish date is not valid.");
        }
    }

    private void validatePublisher(String publisher) {
        if (StringUtils.isEmpty(publisher) || publisher.length() > 20 && publisher.matches(AUTHOR_GENRE_PATTERN)) {
            errorMessages.add("Publisher name is incorrect.");
        }
    }

    private void validatePageCount(String pageCount) {
        if (StringUtils.isEmpty(pageCount) || !pageCount.matches(NUMBER_PATTERN) ||
                (Integer.parseInt(pageCount) < 1 || Integer.parseInt(pageCount) > MAX_PAGE_COUNT)) {
            errorMessages.add("Page count is not valid.");
        }
    }

    private void validateTotalAmount(String totalAmount) {
        if (StringUtils.isEmpty(totalAmount) || !totalAmount.matches(NUMBER_PATTERN) ||
                Integer.parseInt(totalAmount) < 0 ) {
            errorMessages.add("Total amount is not valid.");
        }
    }

    public String validateFile(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String fileName = request.getParameter("currentCover");
        if (!filePart.getSubmittedFileName().equals("")) {
            fileName = filePart.getSubmittedFileName();
            Validator sizeValidator = validatorFactory.validatorCreate(SIZE_VALIDATOR);
            Validator typeValidator = validatorFactory.validatorCreate(TYPE_VALIDATOR);
            Validator nameValidator = validatorFactory.validatorCreate(NAME_VALIDATOR);
            if (!sizeValidator.validate(filePart.getSize()) || !typeValidator.validate(filePart.getContentType())) {
                errorMessages.add("Only jpeg/png up to 2Mb for image.");;
            } else {
                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                Random random = new Random();
                while (!nameValidator.validate(fileName)) {
                    fileName = chars[random.nextInt(chars.length)] + fileName;
                }
            }
        }
        return fileName;
    }
}
