package by.itechart.libmngmt.util.validator;

import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ReaderCardValidator {
    public Set<String> errorMessages = new HashSet<>();

    public static final String USERNAME_PATTERN = "^[a-zA-Z\\s]*$";

    public void validate(HttpServletRequest request) {
        validateEmail(request.getParameter("readerEmail"));
        validateUsername(request.getParameter("readerName"));
    }

    public void validate(String email, String userName) {
        validateEmail(email);
        validateUsername(userName);
    }

    public void validateEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errorMessages.add("Email is incorrect!!");
        }
    }

    public void validateUsername(String userName) {
        if (userName == null || userName.isEmpty() || !userName.matches(USERNAME_PATTERN)) {
            errorMessages.add("Title is incorrect!");
        }
    }
}
