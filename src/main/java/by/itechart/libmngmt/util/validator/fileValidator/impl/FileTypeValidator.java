package by.itechart.libmngmt.util.validator.fileValidator.impl;

import by.itechart.libmngmt.util.validator.fileValidator.Validator;

public class FileTypeValidator implements Validator {
    @Override
    public boolean validate(Object fileType) {
        if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
            return true;
        }
        return false;
    }
}
