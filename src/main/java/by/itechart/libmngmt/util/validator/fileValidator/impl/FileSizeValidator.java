package by.itechart.libmngmt.util.validator.fileValidator.impl;

import by.itechart.libmngmt.util.validator.fileValidator.Validator;

public class FileSizeValidator implements Validator {
    @Override
    public boolean validate(Object fileSize) {
        return (Long)fileSize <= 2097152;
    }
}
