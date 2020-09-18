package by.itechart.libmngmt.util.validator;

import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import by.itechart.libmngmt.util.validator.fileValidator.Validator;
import by.itechart.libmngmt.util.validator.fileValidator.ValidatorFactory;
import org.apache.commons.validator.routines.ISBNValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;



public class BookValidator {
    private final ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();
    public List<String> errorMessages = new ArrayList<>();

    public static final String AUTHOR_GENRE_PATTERN = "^[a-zA-Z\\s]*$";
    public static final String NUMBER_PATTERN = "[0-9]+";

    public void validate(HttpServletRequest request) {
        validateTitle(request.getParameter("title"));
        validateAuthors(Arrays.asList(request.getParameterValues("authors")));
        validateGenres(Arrays.asList(request.getParameterValues("genres")));
        validateIsbn(request.getParameter("ISBN"));
        validatePublishDate(request.getParameter("publishDate"));
        validatePublisher(request.getParameter("publisher"));
        validatePageCount(request.getParameter("pageCount"));
        validateTotalAmount(request.getParameter("totalAmount"),
                Integer.parseInt(request.getParameter("id")));
    }


    private void validateTitle(String title) {
        if (title == null || title.isEmpty() || title.length() > 40) {
            errorMessages.add("Title is incorrect!");
        }
    }

    private void validateAuthors(List<String> authors) {

        if (authors != null) {
            Set<String> authorsSet = new HashSet<>();
            for (String authorName : authors
                 ) {
                if (authorName !=null && !authorName.isEmpty() && authorName.length() < 30
                        && authorName.matches(AUTHOR_GENRE_PATTERN)) {
                    authorsSet.add(authorName.toLowerCase());
                }

            }
            if (authors.size() != authorsSet.size()) {
                errorMessages.add("Author's name(s) is incorrect!");
            }
        } else {
            errorMessages.add("Author's name(s) is incorrect!");
        }
    }

    private void validateGenres(List<String> genres) {
        if (genres != null) {
            Set<String> genresSet = new HashSet<>();
            for (String genre : genres
            ) {
                if (genre !=null && !genre.isEmpty() && genre.length() < 30 && genre.matches(AUTHOR_GENRE_PATTERN)) {
                    genresSet.add(genre.toLowerCase());
                }
            }
            if (genres.size() != genresSet.size()) {
                errorMessages.add("Genre(s) is incorrect!");
            }
        } else {
            errorMessages.add("Genre(s) is incorrect!");
        }
    }

    private void validateIsbn(String isbn) {
        ISBNValidator isbnValidator = ISBNValidator.getInstance();
        if (!isbnValidator.isValid(isbn)) {
            errorMessages.add("ISBN is not valid!");
        }
    }

    private void validatePublishDate(String date) {
        if (!date.matches(NUMBER_PATTERN) || (Integer.parseInt(date) < 1900 ||
                Integer.parseInt(date) > LocalDate.now().getYear())){
            errorMessages.add("Publish date is not valid!");
        }
    }

    private void validatePublisher(String publisher) {
        if (publisher == null || publisher.isEmpty() ||
                publisher.length() > 20 && publisher.matches(AUTHOR_GENRE_PATTERN)) {
            errorMessages.add("Publisher name is incorrect!");
        }
    }

    private void validatePageCount(String pageCount) {
        if (!pageCount.matches(NUMBER_PATTERN) ||
                (Integer.parseInt(pageCount) < 0 || Integer.parseInt(pageCount) > 9999)) {
            errorMessages.add("Page count is not valid!");
        }
    }

    private void validateTotalAmount(String totalAmount, int bookId) {
        if (!totalAmount.matches(NUMBER_PATTERN) || Integer.parseInt(totalAmount) < 0 ||
                Integer.parseInt(totalAmount) < readerCardService.getBorrowBooksCount(bookId)) {
            errorMessages.add("Total amount is not valid!");
        }
    }

    public String validateFile(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String fileName = request.getParameter("currentCover");

        if (!filePart.getSubmittedFileName().equals("")) {
            fileName = request.getParameter("currentCover");

            final int SIZE_VALIDATOR = 1;
            final int TYPE_VALIDATOR = 2;
            final int NAME_VALIDATOR = 3;

            Validator sizeValidator = ValidatorFactory.validatorCreate(SIZE_VALIDATOR);
            Validator typeValidator = ValidatorFactory.validatorCreate(TYPE_VALIDATOR);
            Validator nameValidator = ValidatorFactory.validatorCreate(NAME_VALIDATOR);

            if (sizeValidator.validate(filePart.getSize()) == false ||
                    typeValidator.validate(filePart.getContentType()) == false) {
                errorMessages.add("Only jpeg/png up to 2Mb for image!");;
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
