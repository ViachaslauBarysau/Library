package by.itechart.libmngmt.validation;

import by.itechart.libmngmt.validation.impl.BookValidator;
import by.itechart.libmngmt.validation.impl.ReaderCardValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Validation factory for getting needed validator.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorFactory {
    private static final Logger LOGGER = LogManager.getLogger(ValidatorFactory.class.getName());
    private static final int BOOK_VALIDATOR_INDEX = 1;
    private static final int READER_CARD_VALIDATOR_INDEX = 2;
    private BookValidator bookValidator = BookValidator.getInstance();
    private ReaderCardValidator readerCardValidator = ReaderCardValidator.getInstance();
    private static volatile ValidatorFactory instance;

    public static synchronized ValidatorFactory getInstance() {
        ValidatorFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (ValidatorFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ValidatorFactory();
                }
            }
        }
        return localInstance;
    }

    /**
     * Returns validator depending on the validator type index.
     *
     * @param validatorType validator type index
     * @return Converter object
     */
    public Validator createValidator(final int validatorType) {
        switch (validatorType) {
            case (BOOK_VALIDATOR_INDEX): {
                return bookValidator;
            }
            case (READER_CARD_VALIDATOR_INDEX): {
                return readerCardValidator;
            }
            default: {
                LOGGER.debug("This validator is unknown.");
                throw new RuntimeException("This validator is unknown.");
            }
        }
    }
}
