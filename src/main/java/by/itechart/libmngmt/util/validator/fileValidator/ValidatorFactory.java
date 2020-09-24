package by.itechart.libmngmt.util.validator.fileValidator;

import by.itechart.libmngmt.util.validator.fileValidator.impl.FileNameValidator;
import by.itechart.libmngmt.util.validator.fileValidator.impl.FileSizeValidator;
import by.itechart.libmngmt.util.validator.fileValidator.impl.FileTypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidatorFactory {
    private final static Logger LOGGER = LogManager.getLogger(ValidatorFactory.class.getName());
    final int SIZE_VALIDATOR = 1;
    final int TYPE_VALIDATOR = 2;
    final int NAME_VALIDATOR = 3;

    private static ValidatorFactory instance;
    public static synchronized ValidatorFactory getInstance() {
        if(instance == null){
            instance = new ValidatorFactory();
        }
        return instance;
    }

    public Validator validatorCreate(int validatorType) {
        switch (validatorType) {
            case (SIZE_VALIDATOR): {
                return new FileSizeValidator();
            }
            case (TYPE_VALIDATOR): {
                return new FileTypeValidator();
            }
            case (NAME_VALIDATOR): {
                return new FileNameValidator();
            }
            default: {
                LOGGER.debug("This validator is unknown.");
                throw new RuntimeException("This validator is unknown.");
            }
        }
    }
}
