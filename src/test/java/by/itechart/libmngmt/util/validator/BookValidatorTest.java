package by.itechart.libmngmt.util.validator;

import by.itechart.libmngmt.validation.impl.BookValidator;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class BookValidatorTest {
    @Test
    public void testValidateTitleNotNull() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validTitle = "Title";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateTitle = BookValidator.class.getDeclaredMethod("validateTitle", String.class, List.class);
        validateTitle.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validateTitle.invoke(bookValidator, validTitle, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateTitleNull() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String errorMessage = "Title is incorrect.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateTitle = BookValidator.class.getDeclaredMethod("validateTitle", String.class, List.class);
        validateTitle.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validateTitle.invoke(bookValidator, null, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateAuthorsValidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        List<String> validAuthorsList = Arrays.asList("Pushkin", "Gogol");
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateAuthors = BookValidator.class.getDeclaredMethod("validateAuthors", List.class, List.class);
        validateAuthors.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validateAuthors.invoke(bookValidator, validAuthorsList, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateAuthorsInvalidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        List<String> invalidAuthorsList = Arrays.asList("Pushkin", "pUSHkiN");
        String errorMessage = "Author's name(s) is incorrect.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateAuthors = BookValidator.class.getDeclaredMethod("validateAuthors", List.class, List.class);
        validateAuthors.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validateAuthors.invoke(bookValidator, invalidAuthorsList, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateGenresValidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        List<String> validGenresList = Arrays.asList("horror", "drama");
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateGenres = BookValidator.class.getDeclaredMethod("validateGenres", List.class, List.class);
        validateGenres.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validateGenres.invoke(bookValidator, validGenresList, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateGenresInvalidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        List<String> invalidGenresList = Arrays.asList("horror", "Horror");
        String errorMessage = "Genre(s) is incorrect.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateGenres = BookValidator.class.getDeclaredMethod("validateGenres", List.class, List.class);
        validateGenres.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validateGenres.invoke(bookValidator, invalidGenresList, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateIsbnValidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validIsbn = "978-5-9614-6799-4";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateIsbn = BookValidator.class.getDeclaredMethod("validateIsbn", String.class, List.class);
        validateIsbn.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validateIsbn.invoke(bookValidator, validIsbn, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateIsbnInvalidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String invalidIsbn = "978-5-9614-6799-3";
        String errorMessage = "ISBN is not valid.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateIsbn = BookValidator.class.getDeclaredMethod("validateIsbn", String.class, List.class);
        validateIsbn.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validateIsbn.invoke(bookValidator, invalidIsbn, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidatePublishDateValidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validPublishDate = "2012";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validatePublishDate = BookValidator.class.getDeclaredMethod("validatePublishDate", String.class,
                List.class);
        validatePublishDate.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validatePublishDate.invoke(bookValidator, validPublishDate, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidatePublishDateInvalidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String invalidPublishDate = "-2012";
        String errorMessage = "Publish date is not valid.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validatePublishDate = BookValidator.class.getDeclaredMethod("validatePublishDate", String.class,
                List.class);
        validatePublishDate.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validatePublishDate.invoke(bookValidator, invalidPublishDate, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidatePublisherValidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validPublisher = "World";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validatePublisher = BookValidator.class.getDeclaredMethod("validatePublisher", String.class,
                List.class);
        validatePublisher.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validatePublisher.invoke(bookValidator, validPublisher, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidatePublisherInvalidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String errorMessage = "Publisher name is incorrect.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validatePublisher = BookValidator.class.getDeclaredMethod("validatePublisher", String.class,
                List.class);
        validatePublisher.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validatePublisher.invoke(bookValidator, null, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidatePageCountValidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validPageCount = "333";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validatePageCount = BookValidator.class.getDeclaredMethod("validatePageCount", String.class,
                List.class);
        validatePageCount.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validatePageCount.invoke(bookValidator, validPageCount, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidatePageCountInvalidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String invalidPageCount = "!!!";
        String errorMessage = "Page count is not valid.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validatePageCount = BookValidator.class.getDeclaredMethod("validatePageCount", String.class,
                List.class);
        validatePageCount.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validatePageCount.invoke(bookValidator, invalidPageCount, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateTotalAmountValidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validTotalAmount = "8";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateTotalAmount = BookValidator.class.getDeclaredMethod("validateTotalAmount", String.class,
                List.class);
        validateTotalAmount.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = new ArrayList<>();
        //when
        validateTotalAmount.invoke(bookValidator, validTotalAmount, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateTotalAmountInvalidData() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String errorMessage = "Total amount is not valid.";
        BookValidator bookValidator = BookValidator.getInstance();
        Method validateTotalAmount = BookValidator.class.getDeclaredMethod("validateTotalAmount", String.class,
                List.class);
        validateTotalAmount.setAccessible(true);
        List<String> errorMessages = new ArrayList<>();
        List<String> updatedErrorMessages = Arrays.asList(errorMessage);
        //when
        validateTotalAmount.invoke(bookValidator, null, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }
}
