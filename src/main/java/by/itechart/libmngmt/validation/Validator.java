package by.itechart.libmngmt.validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Validator {
    ValidationResult validate(HttpServletRequest request) throws IOException, ServletException;
}
