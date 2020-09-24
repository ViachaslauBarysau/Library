package by.itechart.libmngmt.util.validator;

import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Getter
public class ReaderCardValidator {
    public static final String USERNAME_PATTERN = "^[a-zA-Z\\s]*$";
    private Set<String> errorMessages = new HashSet<>();

    public void validate(HttpServletRequest request) {
        validateEmail(request.getParameter("readerEmail"));
        validateUsername(request.getParameter("readerName"));
    }

    public void validate(String email, String userName) {
        validateEmail(email);
        validateUsername(userName);
    }

    private void validateEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errorMessages.add("Email in reader card(s) is incorrect!!");
        }
    }

    private void validateUsername(String userName) {
        if (userName == null || userName.isEmpty() || !userName.matches(USERNAME_PATTERN)) {
            errorMessages.add("Name in reader card(s) is incorrect!");
        }
    }
}
