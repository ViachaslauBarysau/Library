package by.itechart.libmngmt.util.validator;

import by.itechart.libmngmt.util.validator.impl.FileNameValidator;
import by.itechart.libmngmt.util.validator.impl.FileSizeValidator;
import by.itechart.libmngmt.util.validator.impl.FileTypeValidator;

public class ValidatorFactory {

    public static Validator validatorCreate(int validatorType) {
        final int SIZE_VALIDATOR = 1;
        final int TYPE_VALIDATOR = 2;
        final int NAME_VALIDATOR = 3;

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
                throw new RuntimeException("This validator is unknown!");
            }
        }
    }

}
