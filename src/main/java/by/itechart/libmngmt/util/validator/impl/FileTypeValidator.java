package by.itechart.libmngmt.util.validator.impl;

import by.itechart.libmngmt.util.validator.Validator;

public class FileTypeValidator implements Validator {

    @Override
    public boolean validate(Object fileType) {

        if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
            return true;
        }
        return false;
    }

}
