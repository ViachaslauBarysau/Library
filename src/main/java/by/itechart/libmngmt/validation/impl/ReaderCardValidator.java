package by.itechart.libmngmt.validation.impl;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.converter.impl.RequestReaderCardDtoListConverter;
import by.itechart.libmngmt.validation.ValidationResult;
import by.itechart.libmngmt.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides methods for validation request parameters.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReaderCardValidator implements Validator {
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    private RequestReaderCardDtoListConverter requestReaderCardDtoListConverter
            = RequestReaderCardDtoListConverter.getInstance();
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

    /**
     * Returns ValidationResult object that contains list with
     * validation error messages.
     *
     * @param request HttpServletRequest
     * @return ValidationResult object
     */
    public ValidationResult validate(final HttpServletRequest request) {
        Set<String> errorMessages = new HashSet<>();
        if (!StringUtils.isEmpty(request.getParameter("readerCards"))) {
            List<ReaderCardDto> readerCards = requestReaderCardDtoListConverter.convertToDto(request);
            for (ReaderCardDto readerCardDto : readerCards) {
                validateEmail(readerCardDto.getReaderEmail(), errorMessages);
                validateUsername(readerCardDto.getReaderName(), errorMessages);
            }
        }
        return ValidationResult.builder()
                .errorList(new ArrayList<>(errorMessages))
                .build();
    }

    private void validateEmail(final String email, final Set<String> errorMessages) {
        if (StringUtils.isEmpty(email) || !email.matches(EMAIL_PATTERN)) {
            errorMessages.add("Email in reader card(s) is incorrect!!");
        }
    }

    private void validateUsername(final String userName, final Set<String> errorMessages) {
        if (StringUtils.isEmpty(userName)) {
            errorMessages.add("Name in reader card(s) is incorrect!");
        }
    }
}
