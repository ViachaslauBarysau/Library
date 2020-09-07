package by.itechart.libmngmt.util.validator.impl;

import by.itechart.libmngmt.util.validator.Validator;

public class FileSizeValidator implements Validator {

    @Override
    public boolean validate(Object fileSize) {

        if (((Long) fileSize) <= 2097152) {
            return true;
        }
        return false;
    }

}
