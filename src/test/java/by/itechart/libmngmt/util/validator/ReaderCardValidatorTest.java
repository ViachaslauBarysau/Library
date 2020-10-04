package by.itechart.libmngmt.util.validator;

import by.itechart.libmngmt.validation.impl.ReaderCardValidator;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ReaderCardValidatorTest {
    @Test
    public void testValidateEmailValidEmail() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validEmail = "invalidemail@gmail.com";
        ReaderCardValidator readerCardValidator = ReaderCardValidator.getInstance();
        Method validateEmail = ReaderCardValidator.class.getDeclaredMethod("validateEmail", String.class,
                Set.class);
        validateEmail.setAccessible(true);
        Set<String> errorMessages = new HashSet<>();
        Set<String> updatedErrorMessages = new HashSet<>();
        //when
        validateEmail.invoke(readerCardValidator, validEmail, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateEmailInvalidEmail() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String errorMessage = "Email in reader card(s) is incorrect!!";
        String invalidEmail = "invalidemailgmail.com";
        ReaderCardValidator readerCardValidator = ReaderCardValidator.getInstance();
        Method validateEmail = ReaderCardValidator.class.getDeclaredMethod("validateEmail", String.class,
                Set.class);
        validateEmail.setAccessible(true);
        Set<String> errorMessages = new HashSet<>();
        Set<String> updatedErrorMessages = new HashSet<>();
        updatedErrorMessages.add(errorMessage);
        //when
        validateEmail.invoke(readerCardValidator, invalidEmail, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateUsernameValidName() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String validEmail = "Viachaslau Barysau";
        ReaderCardValidator readerCardValidator = ReaderCardValidator.getInstance();
        Method validateUsername = ReaderCardValidator.class.getDeclaredMethod("validateUsername", String.class, Set.class);
        validateUsername.setAccessible(true);
        Set<String> errorMessages = new HashSet<>();
        Set<String> updatedErrorMessages = new HashSet<>();
        //when
        validateUsername.invoke(readerCardValidator, validEmail, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }

    @Test
    public void testValidateUsernameInvalidName() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        //given
        String errorMessage = "Name in reader card(s) is incorrect!";
        ReaderCardValidator readerCardValidator = ReaderCardValidator.getInstance();
        Method validateUsername = ReaderCardValidator.class.getDeclaredMethod("validateUsername", String.class,
                Set.class);
        validateUsername.setAccessible(true);
        Set<String> errorMessages = new HashSet<>();
        Set<String> updatedErrorMessages = new HashSet<>();
        updatedErrorMessages.add(errorMessage);
        //when
        validateUsername.invoke(readerCardValidator, null, errorMessages);
        //then
        assertEquals(errorMessages, updatedErrorMessages);
    }
}
