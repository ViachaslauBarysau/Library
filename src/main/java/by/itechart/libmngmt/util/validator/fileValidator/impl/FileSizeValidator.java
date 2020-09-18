package by.itechart.libmngmt.util.validator.fileValidator.impl;

import by.itechart.libmngmt.util.validator.fileValidator.Validator;

public class FileSizeValidator implements Validator {

    @Override
    public boolean validate(Object fileSize) {

        if (((Long) fileSize) <= 2097152) {
            return true;
        }
        return false;
    }

}
