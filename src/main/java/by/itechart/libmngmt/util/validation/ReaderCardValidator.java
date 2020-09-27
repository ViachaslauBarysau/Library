package by.itechart.libmngmt.util.validation;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.util.converter.RequestExtractor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ReaderCardValidator {
    private RequestExtractor requestExtractor = RequestExtractor.getInstance();
    private static final String USERNAME_PATTERN = "^[a-zA-Z\\s]*$";
    private static ReaderCardValidator instance;

    public static synchronized ReaderCardValidator getInstance() {
        ReaderCardValidator localInstance = instance;
        if (localInstance == null) {
            synchronized (ReaderCardValidator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ReaderCardValidator();
                }
            }
        }
        return localInstance;
    }

    public ValidationResult validate(HttpServletRequest request) {
        Set<String> errorMessages = new HashSet<>();
        if (!StringUtils.isEmpty(request.getParameter("readerCards"))) {
            List<ReaderCardDto> readerCards = requestExtractor.extractReaderCardDtoList(request);
            for (ReaderCardDto readerCardDto : readerCards) {
                validateEmail(readerCardDto.getReaderEmail(), errorMessages);
                validateUsername(readerCardDto.getReaderName(), errorMessages);
            }
        }
        ValidationResult validationResult = new ValidationResult(new ArrayList<>(errorMessages));
        return validationResult;
    }

    private void validateEmail(String email, Set<String> errorMessages) {
        if (!EmailValidator.getInstance().isValid(email)) {
            errorMessages.add("Email in reader card(s) is incorrect!!");
        }
    }

    private void validateUsername(String userName, Set<String> errorMessages) {
        if (userName == null || userName.isEmpty() || !userName.matches(USERNAME_PATTERN)) {
            errorMessages.add("Name in reader card(s) is incorrect!");
        }
    }
}
